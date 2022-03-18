package com.example.laptop.controller;

import com.example.laptop.db.model.CommentModel;
import com.example.laptop.security.jwt.JwtUtils;
import com.example.laptop.service.CommentService;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.PreAuthorizerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "https://laptopre.herokuapp.com")
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping()
    public ResponseEntity<?> getAllByProduct(Long productId) {
        return commentService.getAllByProduct(productId);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getById(@PathVariable Long commentId) {
        return commentService.getById(commentId);
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @PostMapping()
    public ResponseEntity<?> create(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody CommentModel commentModel) {
        return commentService.create(commentModel, jwtUtils.getUserIdFromJwtToken(token));
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @PutMapping()
    public ResponseEntity<?> update(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @RequestBody CommentModel commentModel) {
        return commentService.update(commentModel, jwtUtils.getUserIdFromJwtToken(token));
    }

    @PreAuthorize(PreAuthorizerConstants.ROLE_USER)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@RequestHeader(Constants.AUTHORIZATION) String token,
                                    @PathVariable Long commentId) {
        return commentService.delete(commentId, jwtUtils.getUserIdFromJwtToken(token));
    }
}
