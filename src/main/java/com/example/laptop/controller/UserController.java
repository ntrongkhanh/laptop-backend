package com.example.laptop.controller;

import com.example.laptop.db.model.LoginModel;
import com.example.laptop.db.model.UserModel;
import com.example.laptop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserModel userModel) {
        return userService.create(userModel);
    }

    @GetMapping("/active")
    public ResponseEntity<?> active(@RequestParam String code, @RequestParam String email) {
        return userService.active(code, email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel loginModel) {
        return userService.login(loginModel);
    }
}
