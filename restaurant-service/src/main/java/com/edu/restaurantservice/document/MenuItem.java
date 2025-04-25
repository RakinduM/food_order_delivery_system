package com.edu.restaurantservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "menuItems")

public class MenuItem {

    @Id
    private String id;
    private String restaurantId;
    private String name;
    private String description;
    private MenuItemCategory category;
    private String imageUrl;
    private Double price;
    private MenuItemPortion portion;
    private Boolean is_available;

    private List<MenuItem> menuItems;
    private Instant createdAt;
    private Instant updatedAt;
}
