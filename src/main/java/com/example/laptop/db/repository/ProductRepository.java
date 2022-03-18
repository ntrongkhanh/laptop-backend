package com.example.laptop.db.repository;

import com.example.laptop.db.entity.Product;
import com.example.laptop.db.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductTypeAndActiveTrueOrderByIdDesc(ProductType productType);

    Page<Product> findByNameContainingAndProductTypeAndActiveTrueOrderByIdDesc(String keyword, ProductType productType, Pageable pageable);

    Page<Product> findByProductTypeAndActiveTrueOrderByIdDesc(ProductType productType, Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(Long id);
}
