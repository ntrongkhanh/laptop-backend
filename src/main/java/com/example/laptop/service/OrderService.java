package com.example.laptop.service;

import com.example.laptop.db.entity.Order;
import com.example.laptop.db.entity.OrderDetail;
import com.example.laptop.db.entity.Product;
import com.example.laptop.db.entity.User;
import com.example.laptop.db.enums.OrderStatus;
import com.example.laptop.db.mapper.OrderMapper;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.model.OrderModel;
import com.example.laptop.db.repository.OrderDetailRepository;
import com.example.laptop.db.repository.OrderRepository;
import com.example.laptop.db.repository.UserRepository;
import com.example.laptop.mail.MailService;
import com.example.laptop.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public ResponseEntity<?> getAllByUser(Long userId) {
        List<Order> orderEntities = orderRepository.findByUserId(userId);
        List<OrderModel> models = orderMapper.toModel(orderEntities);
        return new ResponseEntity<>(new ModelPage<>(models), HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ORDER_NOT_FOUND));
        User user = userRepository.findByIdAndActiveTrue(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));
        if (!order.getUser().getId().equals(userId) && !user.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ModelPage<>(orderMapper.toModel(order)), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> create(OrderModel orderModel, Long userId) {
        long totalPrice = 0;
        User user = userRepository.findByIdAndActiveTrue(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ACCOUNT_NOT_FOUND));

        Order order = new Order();
        order.setAddress(orderModel.getAddress());
        order.setStatus(OrderStatus.WAITING);
        order.setName(orderModel.getName());
        order.setPhone(orderModel.getPhone());
        order.setUser(user);
        order = orderRepository.saveAndFlush(order);

        List<OrderDetail> orderDetailEntities = new ArrayList<>();
        for (int i = 0; i < orderModel.getOrderDetailModels().size(); i++) {
            Product product = entityManager.getReference(Product.class, orderModel.getOrderDetailModels().get(i).getProductId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(orderModel.getOrderDetailModels().get(i).getQuantity());
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);
            orderDetail.setTotalPrice(product.getPrice() * orderDetail.getQuantity());
            totalPrice += orderDetail.getTotalPrice();
            orderDetail = orderDetailRepository.saveAndFlush(orderDetail);
        }
        order = orderRepository.findByIdAndActiveTrue(order.getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.ORDER_NOT_FOUND));
        order.setTotalPrice(totalPrice);
        try {
            order.setOrderDetails(orderDetailEntities);
            mailService.sendOrderMail(sender, order);
            order = orderRepository.save(order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new ModelPage<>(orderMapper.toModel(order)), HttpStatus.OK);
    }

    public ResponseEntity<?> cancel() {
        return null;
    }
}
