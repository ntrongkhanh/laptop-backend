package com.example.laptop.db.repository;

import com.example.laptop.db.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    Optional<CartDetail> findByIdAndActiveTrue(Long id);

    List<CartDetail> findByCartUserIdAndActiveTrue(Long id);
}
