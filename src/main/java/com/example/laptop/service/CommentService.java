package com.example.laptop.service;

import com.example.laptop.db.entity.Comment;
import com.example.laptop.db.entity.Product;
import com.example.laptop.db.entity.User;
import com.example.laptop.db.mapper.CommentMapper;
import com.example.laptop.db.model.CommentModel;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.repository.CommentRepository;
import com.example.laptop.db.repository.ProductRepository;
import com.example.laptop.db.repository.UserRepository;
import com.example.laptop.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> getAllByProduct(Long productId) {
        List<Comment> commentEntities = commentRepository.findByProductIdAndActiveTrueAndParentIsNullOrderByIdDesc(productId);

        return new ResponseEntity<>(new ModelPage<>(commentMapper.toModel(commentEntities)), HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long commentId) {
        Comment comment = commentRepository.findByIdAndActiveTrue(commentId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.COMMENT_NOT_FOUND));

        return new ResponseEntity<>(new ModelPage<>(commentMapper.toModel(comment)), HttpStatus.OK);
    }

    public ResponseEntity<?> create(CommentModel commentModel, Long userId) {
        Comment comment = new Comment();
        User user = userRepository.findByIdAndActiveTrue(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));
        Comment parent = null;
        if (commentModel.getParentId() != null && commentModel.getParentId() != 0) {
            parent = commentRepository.findByIdAndActiveTrue(commentModel.getParentId()).orElseThrow(()
                    -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));
        }
        Product product = productRepository.findByIdAndActiveTrue(commentModel.getProductId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));

        comment.setParent(parent);
        comment.setUser(user);
        comment.setProduct(product);
        comment.setContent(commentModel.getContent());
        comment = commentRepository.save(comment);

        return new ResponseEntity<>(new ModelPage<>(commentMapper.toModel(comment)), HttpStatus.OK);
    }

    public ResponseEntity<?> update(CommentModel commentModel, Long userId) {
        Comment comment = commentRepository.findByIdAndActiveTrue(commentModel.getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.COMMENT_NOT_FOUND));
        User user = userRepository.findByIdAndActiveTrue(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        comment.setContent(commentModel.getContent());
        comment.setUpdatedDate(new Date());
        comment = commentRepository.save(comment);
        return new ResponseEntity<>(new ModelPage<>(commentMapper.toModel(comment)), HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findByIdAndActiveTrue(commentId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.COMMENT_NOT_FOUND));
        if (!comment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        comment.setActive(false);
        commentRepository.save(comment);

        return new ResponseEntity<>(new ModelPage<>(MessageConstants.SUCCESS), HttpStatus.OK);
    }
}
