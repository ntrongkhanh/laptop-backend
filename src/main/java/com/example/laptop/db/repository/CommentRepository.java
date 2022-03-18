package com.example.laptop.db.repository;

import com.example.laptop.db.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByProductIdAndActiveTrueAndParentIsNullOrderByIdDesc(Long id);

    Optional<Comment> findByIdAndActiveTrue(Long id);
}
