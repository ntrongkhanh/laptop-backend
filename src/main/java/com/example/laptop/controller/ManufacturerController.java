package com.example.laptop.controller;

import com.example.laptop.db.model.ManufacturerModel;
import com.example.laptop.service.ManufacturerService;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.PreAuthorizerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return manufacturerService.getAll();
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<?> getById(Long manufacturerId) {
        return manufacturerService.getById(manufacturerId);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PostMapping()
    public ResponseEntity<?> create(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody ManufacturerModel manufacturerModel) {
        return manufacturerService.create(manufacturerModel);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @PutMapping()
    public ResponseEntity<?> update(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody ManufacturerModel manufacturerModel) {
        return manufacturerService.update(manufacturerModel);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_ADMIN)
    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<?> delete(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @PathVariable Long manufacturerId) {
        return manufacturerService.delete(manufacturerId);
    }
}
