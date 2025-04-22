package com.edu.restaurantservice.dto;

import com.edu.restaurantservice.document.MenuItemCategory;
import com.edu.restaurantservice.document.MenuItemPortion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponseDTO {
    private String id;
    private String restaurantId;
    private MenuItemCategory category;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private MenuItemPortion portion;
    private Boolean is_available;
    private List<MenuItemResponseDTO> menuItems;
    private Instant createdAt;
    private Instant updatedAt;
}