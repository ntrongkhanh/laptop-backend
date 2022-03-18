package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.Image;
import com.example.laptop.db.entity.Laptop;
import com.example.laptop.db.entity.Product;
import com.example.laptop.db.model.ImageModel;
import com.example.laptop.db.model.LaptopModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class LaptopMapper implements EntityMapper<LaptopModel, Laptop> {

    @Autowired
    private ImageMapper imageMapper;

    @Mapping(source = "manufacturer.name", target = "manufacturer")
    @Mapping(source = "manufacturer.id", target = "manufacturerId")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "laptop.ram", target = "ram")
    @Mapping(source = "imageEntities", target = "images", qualifiedByName = "toImageModel")
    public abstract LaptopModel productToModel(Product content);

    public List<LaptopModel> productToModel(List<Product> content){
        List<LaptopModel> laptopModels=new ArrayList<>();
        content.forEach(product -> laptopModels.add(productToModel(product)));
        return laptopModels;
    }

    @Mapping(ignore = true, target = "manufacturer")
    @Mapping(ignore = true, target = "category")
    @Mapping(ignore = true, target = "ram")
    public abstract Product modelToEntity(LaptopModel content);

    @Named("toImageModel")
    public List<ImageModel> toImageModel(List<Image> images) {
        return imageMapper.toModel(images);
    }
}
