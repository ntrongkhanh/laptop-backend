package com.example.laptop.service;

import com.example.laptop.db.entity.Category;
import com.example.laptop.db.mapper.CategoryMapper;
import com.example.laptop.db.model.CategoryModel;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public ResponseEntity<?> getAll() {
        List<Category> categoryEntities = categoryRepository.findByActiveTrue();

        return new ResponseEntity<>(new ModelPage<>(categoryMapper.toModel(categoryEntities)), HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long categoryId) {
        Category category = categoryRepository.findByIdAndActiveTrue(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<>(new ModelPage<>(categoryMapper.toModel(category)), HttpStatus.OK);
    }

    public ResponseEntity<?> create(CategoryModel categoryModel) {
        Category category = categoryMapper.toEntity(categoryModel);
        category = categoryRepository.save(category);

        return new ResponseEntity<>(new ModelPage<>(categoryMapper.toModel(category)), HttpStatus.OK);
    }

    public ResponseEntity<?> update(CategoryModel categorymodel) {
        Category category = categoryRepository.findByIdAndActiveTrue(categorymodel.getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        category.setProductType(categorymodel.getProductType() != null ?
                categorymodel.getProductType() : category.getProductType());
        category.setName(categorymodel.getName() != null ? categorymodel.getName() : category.getName());
        category.setUpdatedDate(new Date());

        category = categoryRepository.save(category);

        return new ResponseEntity<>(new ModelPage<>(categoryMapper.toModel(category)), HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long categoryId) {
        Category category = categoryRepository.findByIdAndActiveTrue(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        category.setActive(false);
        categoryRepository.save(category);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}