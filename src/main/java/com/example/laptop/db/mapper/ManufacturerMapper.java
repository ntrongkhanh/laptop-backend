package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.Manufacturer;
import com.example.laptop.db.model.ManufacturerModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class ManufacturerMapper implements EntityMapper<ManufacturerModel, Manufacturer> {
}
