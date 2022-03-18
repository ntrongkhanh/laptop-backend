package com.example.laptop.controller;

import com.example.laptop.security.jwt.JwtUtils;
import com.example.laptop.service.CartDetailService;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.PreAuthorizerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/cart-detail")
public class CartDetailController {

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @GetMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestHeader(Constants.AUTHORIZATION) String token,
                                              @RequestParam Long productId,
                                              @RequestParam Integer quantity) {
        return cartDetailService.addProductToCart(productId, jwtUtils.getUserIdFromJwtToken(token), quantity);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @PutMapping("/{cartDetailId}")
    public ResponseEntity<?> update(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @PathVariable Long cartDetailId,
                                    @RequestParam Integer quantity) {
        return cartDetailService.update(cartDetailId,  jwtUtils.getUserIdFromJwtToken(token), quantity);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @DeleteMapping("/{cartDetailId}")
    public ResponseEntity<?> delete(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @PathVariable Long cartDetailId) {
        return cartDetailService.delete(cartDetailId, jwtUtils.getUserIdFromJwtToken(token));
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @GetMapping()
    public ResponseEntity<?> getAllByUser(@RequestHeader(Constants.AUTHORIZATION) String token) {
        return cartDetailService.getAllByUser(jwtUtils.getUserIdFromJwtToken(token));
    }
}
