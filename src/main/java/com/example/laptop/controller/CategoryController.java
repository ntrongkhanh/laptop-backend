package com.example.laptop.controller;

import com.example.laptop.db.model.CategoryModel;
import com.example.laptop.security.jwt.JwtUtils;
import com.example.laptop.service.CategoryService;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.PreAuthorizerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PostMapping()
    public ResponseEntity<?> create(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody CategoryModel categoryModel) {
        return categoryService.create(categoryModel);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PutMapping()
    public ResponseEntity<?> update(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody CategoryModel categoryModel) {
        return categoryService.update(categoryModel);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> delete(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @PathVariable Long categoryId) {
        return categoryService.delete(categoryId);
    }
}
