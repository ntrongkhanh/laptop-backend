package com.example.laptop.service;

import com.example.laptop.db.entity.Cart;
import com.example.laptop.db.entity.CartDetail;
import com.example.laptop.db.entity.Product;
import com.example.laptop.db.mapper.CartDetailMapper;
import com.example.laptop.db.mapper.CartMapper;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.repository.CartDetailRepository;
import com.example.laptop.db.repository.CartRepository;
import com.example.laptop.db.repository.ProductRepository;
import com.example.laptop.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartDetailMapper cartDetailMapper;

    @Autowired
    private CartMapper cartMapper;

    public ResponseEntity<?> addProductToCart(Long productId, Long userId, Integer quantity) {
        Cart cart = cartRepository.findByUserIdAndActiveTrue(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));

        Product product = productRepository.findByIdAndActiveTrue(productId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));

        CartDetail cartDetail = new CartDetail(quantity, cart, product);
        cartDetail.setTotalPrice(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
        cartDetail = cartDetailRepository.saveAndFlush(cartDetail);
        cart.setTotalPrice(cartDetail.getTotalPrice() + cartDetail.getTotalPrice());
        cart = cartRepository.save(cart);

        ModelPage data = new ModelPage(cartMapper.toModel(cart));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long cartDetailId, Long userId, Integer quantity) {
        CartDetail cartDetail = cartDetailRepository.findByIdAndActiveTrue(cartDetailId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.CART_DETAIL_NOT_FOUND));
        if (!cartDetail.getCart().getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        cartDetail.setQuantity(quantity);
        cartDetail.setUpdatedDate(new Date());
        cartDetail = cartDetailRepository.save(cartDetail);

        ModelPage data = new ModelPage(cartDetailMapper.toModel(cartDetail));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long cartDetailId, Long userId) {
        CartDetail cartDetail = cartDetailRepository.findByIdAndActiveTrue(cartDetailId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.CART_DETAIL_NOT_FOUND));
        if (!cartDetail.getCart().getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        cartDetail.setActive(false);
        cartDetailRepository.save(cartDetail);

        ModelPage data = new ModelPage(MessageConstants.SUCCESS);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllByUser(Long userId) {
        List<CartDetail> cartDetails = cartDetailRepository.findByCartUserIdAndActiveTrue(userId);
        ModelPage data = new ModelPage(cartDetailMapper.toModel(cartDetails));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
