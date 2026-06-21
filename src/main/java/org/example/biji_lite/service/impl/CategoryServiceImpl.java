package org.example.biji_lite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.BusinessException;
import org.example.biji_lite.common.UserContext;
import org.example.biji_lite.dto.CategoryDTO;
import org.example.biji_lite.entity.Category;
import org.example.biji_lite.entity.Note;
import org.example.biji_lite.mapper.CategoryMapper;
import org.example.biji_lite.mapper.NoteMapper;
import org.example.biji_lite.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final NoteMapper noteMapper;

    @Override
    public List<Category> listCategories() {
        Long userId = UserContext.getUserId();

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getUserId, userId);
        queryWrapper.orderByDesc(Category::getCreateTime);

        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(CategoryDTO categoryDTO) {
        Long userId = UserContext.getUserId();

        // 检查同名分类是否已存在
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getUserId, userId);
        queryWrapper.eq(Category::getName, categoryDTO.getName());
        Long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(400, "分类名称已存在");
        }

        Category category = new Category();
        category.setUserId(userId);
        category.setName(categoryDTO.getName());

        categoryMapper.insert(category);
        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Long id, CategoryDTO categoryDTO) {
        Long userId = UserContext.getUserId();

        // 检查分类是否存在且属于当前用户
        Category existingCategory = categoryMapper.selectById(id);
        if (existingCategory == null) {
            throw new BusinessException(404, "分类不存在");
        }
        if (!existingCategory.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权修改他人的分类");
        }

        // 检查同名分类是否已存在（排除自己）
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getUserId, userId);
        queryWrapper.eq(Category::getName, categoryDTO.getName());
        queryWrapper.ne(Category::getId, id);
        Long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(400, "分类名称已存在");
        }

        Category category = new Category();
        category.setId(id);
        category.setName(categoryDTO.getName());

        categoryMapper.updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        Long userId = UserContext.getUserId();

        // 检查分类是否存在且属于当前用户
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(404, "分类不存在");
        }
        if (!category.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权删除他人的分类");
        }

        // 检查是否还有笔记关联此分类
        LambdaQueryWrapper<Note> noteQuery = new LambdaQueryWrapper<>();
        noteQuery.eq(Note::getCategoryId, id);
        Long noteCount = noteMapper.selectCount(noteQuery);
        if (noteCount > 0) {
            throw new BusinessException(400, "该分类下还有" + noteCount + "篇笔记，请先移动或删除这些笔记");
        }

        categoryMapper.deleteById(id);
    }
}