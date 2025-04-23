package com.edu.restaurantservice.repository;

import com.edu.restaurantservice.document.MenuItem;
import com.edu.restaurantservice.dto.FoodSalesReportDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MenuItemRepository extends MongoRepository<MenuItem, String> {
    List<MenuItem> findByRestaurantId(String restaurantId);

    @Aggregation(pipeline = {
            "{ $match: { restaurantId: ?0 } }",
            "{ $lookup: { from: 'orders', localField: '_id', foreignField: 'items.menuItemId', as: 'orders' } }",
            "{ $unwind: '$orders' }",
            "{ $unwind: '$orders.items' }",
            "{ $match: { 'orders.createdAt': { $gte: ?1, $lte: ?2 } } }",
            "{ $match: { $expr: { $eq: ['$orders.items.menuItemId', '$_id'] } } }",
            "{ $group: { _id: '$_id', name: { $first: '$name' }, totalQuantity: { $sum: '$orders.items.quantity' }, totalRevenue: { $sum: { $multiply: ['$orders.items.quantity', '$orders.items.price'] } } } }",
            "{ $project: { menuItemId: '$_id', menuItemName: '$name', quantitySold: '$totalQuantity', totalRevenue: '$totalRevenue', _id: 0 } }"
    })
    List<FoodSalesReportDTO> getSalesReportByRestaurantAndDateRange(String restaurantId, LocalDateTime startDate, LocalDateTime endDate);
}