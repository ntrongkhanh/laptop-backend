package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.Comment;
import com.example.laptop.db.model.CommentModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class CommentMapper implements EntityMapper<CommentModel, Comment> {
}
