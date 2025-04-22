package com.edu.restaurantservice.controller;

import com.edu.restaurantservice.dto.MenuItemRequestDTO;
import com.edu.restaurantservice.dto.MenuItemResponseDTO;
import com.edu.restaurantservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> createMenuItem(@RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        MenuItemResponseDTO createdItem = menuItemService.createMenuItem(menuItemRequestDTO);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {
        List<MenuItemResponseDTO> menuItems = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable String id) {
        MenuItemResponseDTO menuItem = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(menuItem);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemResponseDTO>> getMenuItemsByRestaurantId(@PathVariable String restaurantId) {
        List<MenuItemResponseDTO> menuItems = menuItemService.getMenuItemsByRestaurantId(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> updateMenuItem(
            @PathVariable String id,
            @RequestBody MenuItemRequestDTO menuItemRequestDTO) {
        MenuItemResponseDTO updatedItem = menuItemService.updateMenuItem(id, menuItemRequestDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable String id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<MenuItemResponseDTO> updateAvailability(
            @PathVariable String id,
            @RequestParam Boolean isAvailable) {
        MenuItemResponseDTO updatedItem = menuItemService.updateAvailability(id, isAvailable);
        return ResponseEntity.ok(updatedItem);
    }
}