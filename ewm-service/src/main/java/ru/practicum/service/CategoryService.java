package ru.practicum.service;

import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long id);

    CategoryDto addNewCategory(NewCategoryDto category);

    void deleteCategoryById(Long id);

    CategoryDto updateCategory(Long id, CategoryDto category);
}