package org.example.biji_lite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.Result;
import org.example.biji_lite.dto.CategoryDTO;
import org.example.biji_lite.entity.Category;
import org.example.biji_lite.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 获取分类列表
     * GET /api/category/list
     */
    @GetMapping("/list")
    public Result<List<Category>> listCategories() {
        List<Category> categories = categoryService.listCategories();
        return Result.success(categories);
    }

    /**
     * 创建分类
     * POST /api/category
     */
    @PostMapping
    public Result<Long> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        Long categoryId = categoryService.createCategory(categoryDTO);
        return Result.success("创建成功", categoryId);
    }

    /**
     * 更新分类
     * PUT /api/category/{id}
     */
    @PutMapping("/{id}")
    public Result<Void> updateCategory(@PathVariable Long id,
                                       @RequestBody @Valid CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return Result.success("更新成功", null);
    }

    /**
     * 删除分类
     * DELETE /api/category/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success("删除成功", null);
    }
}