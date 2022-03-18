package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.CartDetail;
import com.example.laptop.db.entity.OrderDetail;
import com.example.laptop.db.model.CartDetailModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class CartDetailMapper implements EntityMapper<CartDetailModel, CartDetail> {
}