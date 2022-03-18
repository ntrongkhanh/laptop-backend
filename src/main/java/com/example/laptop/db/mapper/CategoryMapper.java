package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.Category;
import com.example.laptop.db.model.CategoryModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class CategoryMapper implements EntityMapper<CategoryModel, Category> {
}
