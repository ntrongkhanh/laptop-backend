package com.example.laptop.db.repository;

import com.example.laptop.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long id);

    Optional<Order> findByIdAndActiveTrue(Long id);
}
