package com.example.laptop.db.repository;

import com.example.laptop.db.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    Optional<OrderDetail> findByIdAndActiveTrue(Long id);
}
