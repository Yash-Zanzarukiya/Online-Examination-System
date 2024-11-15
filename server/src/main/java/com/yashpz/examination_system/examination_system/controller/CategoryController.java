package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.CategoryDTO;
import com.yashpz.examination_system.examination_system.service.CategoryService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, savedCategory, "Category created successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ApiResponseUtil.handleResponse(HttpStatus.OK, categories, "Categories retrieved successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable UUID id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, category, "Category retrieved successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updatedCategory, "Category updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Category deleted successfully");
    }
}
