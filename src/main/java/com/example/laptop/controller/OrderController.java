package com.example.laptop.controller;

import com.example.laptop.db.model.OrderModel;
import com.example.laptop.security.jwt.JwtUtils;
import com.example.laptop.service.OrderService;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.PreAuthorizerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwtUtils;

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @GetMapping()
    public ResponseEntity<?> getAllByUser(@RequestHeader(Constants.AUTHORIZATION) String token) {
        return orderService.getAllByUser(jwtUtils.getUserIdFromJwtToken(token));
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getById(@RequestHeader(Constants.AUTHORIZATION) String token,
                                     @PathVariable Long orderId) {
        return orderService.getById(orderId, jwtUtils.getUserIdFromJwtToken(token));
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @PostMapping()
    public ResponseEntity<?> create(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody OrderModel orderModel) {
        return orderService.create(orderModel, jwtUtils.getUserIdFromJwtToken(token));
    }
}
