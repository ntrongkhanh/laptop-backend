package com.example.laptop.controller;

import com.example.laptop.db.model.LaptopModel;
import com.example.laptop.service.LaptopService;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.PreAuthorizerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/laptop")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return laptopService.getAll();
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(required = false) String keyword,
                                    @RequestParam(defaultValue = Constants.DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return laptopService.filter(keyword, page, size);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getById(@PathVariable Long productId) {
        return laptopService.getById(productId);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PostMapping()
    public ResponseEntity<?> create(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody LaptopModel laptopModel) {
        return laptopService.create(laptopModel);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PutMapping()
    public ResponseEntity<?> update(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody LaptopModel laptopModel) {
        return laptopService.update(laptopModel);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @PathVariable Long productId) {
        return laptopService.delete(productId);
    }
}
