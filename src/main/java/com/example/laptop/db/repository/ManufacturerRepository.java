package com.example.laptop.db.repository;

import com.example.laptop.db.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByIdAndActiveTrue(Long id);

    List<Manufacturer> findByActiveTrueOrderByIdDesc();
}
