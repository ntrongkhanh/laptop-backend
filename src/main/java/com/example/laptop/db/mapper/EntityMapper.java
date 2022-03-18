package com.example.laptop.db.mapper;

import org.mapstruct.*;

import java.util.List;


public interface EntityMapper<M, E> {

    @Mapping(ignore = true,target = "id")
    @Mapping(ignore = true,target = "createdDate")
    @Mapping(ignore = true,target = "updatedDate")
    E toEntity(M model);

    M toModel(E entity);

    List<E> toEntity(List<M> dtoList);

    List<M> toModel(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, M model);

    @Named("toUpdateEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    void toUpdateEntity(@MappingTarget E entity, M model);
}
