package org.example.biji_lite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.biji_lite.entity.Category;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}