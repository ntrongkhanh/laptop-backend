package com.example.laptop.controller;

import com.example.laptop.service.ImageService;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.PreAuthorizerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PostMapping()
    public ResponseEntity<?> create(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestParam MultipartFile file) {
        return imageService.create(file);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getById(@PathVariable Long imageId) {
        return imageService.getById(imageId);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PutMapping("/{imageId}")
    public ResponseEntity<?> update(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestParam MultipartFile file,
                                    @PathVariable Long imageId) {
        return imageService.update(file, imageId);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> delete(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @PathVariable Long imageId) {
        return imageService.delete(imageId);
    }
}