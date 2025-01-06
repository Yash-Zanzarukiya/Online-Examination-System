package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.CategoryDTO;
import com.yashpz.examination_system.examination_system.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName().toLowerCase().trim());
        return category;
    }

    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
