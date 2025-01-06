package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.CategoryDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.CategoryMapper;
import com.yashpz.examination_system.examination_system.model.Category;
import com.yashpz.examination_system.examination_system.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "Category name already exists");
        }
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO(savedCategory);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Category not found"));
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Category not found"));

        if (!category.getName().equals(categoryDTO.getName())
                && categoryRepository.existsByName(categoryDTO.getName())) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "Category name already exists");
        }

        category.setName(categoryDTO.getName());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ApiError(HttpStatus.NOT_FOUND, "Category not found");
        }
        categoryRepository.deleteById(id);
    }

    public Category fetchCategoryByName(String name) {
        Category category = categoryRepository.findByName(name.toLowerCase().trim());
        if (category == null)
            throw new ApiError(HttpStatus.NOT_FOUND, "Category not found");
        return category;
    }

    public UUID fetchCategoryIdByName(String name) {
        return fetchCategoryByName(name).getId();
    }
}
