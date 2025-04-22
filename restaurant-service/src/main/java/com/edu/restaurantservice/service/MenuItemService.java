package com.edu.restaurantservice.service;

import com.edu.restaurantservice.dto.MenuItemRequestDTO;
import com.edu.restaurantservice.dto.MenuItemResponseDTO;

import java.util.List;

public interface MenuItemService {
    MenuItemResponseDTO createMenuItem(MenuItemRequestDTO menuItemRequestDTO);
    List<MenuItemResponseDTO> getAllMenuItems();
    MenuItemResponseDTO getMenuItemById(String id);
    List<MenuItemResponseDTO> getMenuItemsByRestaurantId(String restaurantId);
    MenuItemResponseDTO updateMenuItem(String id, MenuItemRequestDTO menuItemRequestDTO);
    void deleteMenuItem(String id);
    MenuItemResponseDTO updateAvailability(String id, Boolean isAvailable);
}