package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.OrderDetail;
import com.example.laptop.db.model.OrderDetailModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class OrderDetailMapper implements EntityMapper<OrderDetailModel, OrderDetail> {
}
