package com.example.laptop.db.repository;

import com.example.laptop.db.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByActiveTrue();

    Optional<Image> findByIdAndActiveTrue(Long id);
}
