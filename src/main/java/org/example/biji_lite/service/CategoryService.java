package org.example.biji_lite.service;

import org.example.biji_lite.dto.CategoryDTO;
import org.example.biji_lite.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listCategories();

    Long createCategory(CategoryDTO categoryDTO);

    void updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}