package com.edu.restaurantservice.service;

import com.edu.restaurantservice.document.MenuItem;
import com.edu.restaurantservice.dto.MenuItemRequestDTO;
import com.edu.restaurantservice.dto.MenuItemResponseDTO;
import com.edu.restaurantservice.exception.ResourceNotFoundException;
import com.edu.restaurantservice.repository.MenuItemRepository;
import com.edu.restaurantservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuItemResponseDTO createMenuItem(MenuItemRequestDTO menuItemRequestDTO) {
        MenuItem menuItem = mapToMenuItem(menuItemRequestDTO);
        menuItem.setCreatedAt(Instant.now());
        menuItem.setUpdatedAt(Instant.now());
        MenuItem savedItem = menuItemRepository.save(menuItem);
        return mapToMenuItemResponseDTO(savedItem);
    }

    @Override
    public List<MenuItemResponseDTO> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(this::mapToMenuItemResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponseDTO getMenuItemById(String id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        return mapToMenuItemResponseDTO(menuItem);
    }

    @Override
    public List<MenuItemResponseDTO> getMenuItemsByRestaurantId(String restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(this::mapToMenuItemResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponseDTO updateMenuItem(String id, MenuItemRequestDTO menuItemRequestDTO) {
        MenuItem existingItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));

        existingItem.setRestaurantId(menuItemRequestDTO.getRestaurantId());
        existingItem.setCategory(menuItemRequestDTO.getCategory());
        existingItem.setName(menuItemRequestDTO.getName());
        existingItem.setDescription(menuItemRequestDTO.getDescription());
        existingItem.setImageUrl(menuItemRequestDTO.getImageUrl());
        existingItem.setPrice(menuItemRequestDTO.getPrice());
        existingItem.setPortion(menuItemRequestDTO.getPortion());
        existingItem.setIs_available(menuItemRequestDTO.getIs_available());
        existingItem.setMenuItems(mapToMenuItemList(menuItemRequestDTO.getMenuItems()));
        existingItem.setUpdatedAt(Instant.now());

        MenuItem updatedItem = menuItemRepository.save(existingItem);
        return mapToMenuItemResponseDTO(updatedItem);
    }

    @Override
    public void deleteMenuItem(String id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        menuItemRepository.delete(menuItem);
    }

    @Override
    public MenuItemResponseDTO updateAvailability(String id, Boolean isAvailable) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        menuItem.setIs_available(isAvailable);
        menuItem.setUpdatedAt(Instant.now());
        MenuItem updatedItem = menuItemRepository.save(menuItem);
        return mapToMenuItemResponseDTO(updatedItem);
    }

    private MenuItem mapToMenuItem(MenuItemRequestDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurantId(dto.getRestaurantId());
        menuItem.setCategory(dto.getCategory());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setImageUrl(dto.getImageUrl());
        menuItem.setPrice(dto.getPrice());
        menuItem.setPortion(dto.getPortion());
        menuItem.setIs_available(dto.getIs_available());
        menuItem.setMenuItems(mapToMenuItemList(dto.getMenuItems()));
        return menuItem;
    }

    private List<MenuItem> mapToMenuItemList(List<MenuItemRequestDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::mapToMenuItem)
                .collect(Collectors.toList());
    }

    private MenuItemResponseDTO mapToMenuItemResponseDTO(MenuItem menuItem) {
        MenuItemResponseDTO dto = new MenuItemResponseDTO();
        dto.setId(menuItem.getId());
        dto.setRestaurantId(menuItem.getRestaurantId());
        dto.setCategory(menuItem.getCategory());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setPrice(menuItem.getPrice());
        dto.setPortion(menuItem.getPortion());
        dto.setIs_available(menuItem.getIs_available());
        dto.setMenuItems(mapToMenuItemResponseDTOList(menuItem.getMenuItems()));
        dto.setCreatedAt(menuItem.getCreatedAt());
        dto.setUpdatedAt(menuItem.getUpdatedAt());
        return dto;
    }

    private List<MenuItemResponseDTO> mapToMenuItemResponseDTOList(List<MenuItem> menuItems) {
        if (menuItems == null) {
            return null;
        }
        return menuItems.stream()
                .map(this::mapToMenuItemResponseDTO)
                .collect(Collectors.toList());
    }
}