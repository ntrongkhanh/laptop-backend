package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.Image;
import com.example.laptop.db.model.ImageModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class ImageMapper implements EntityMapper<ImageModel, Image> {
}