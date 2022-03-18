package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.Cart;
import com.example.laptop.db.entity.CartDetail;
import com.example.laptop.db.entity.Order;
import com.example.laptop.db.entity.OrderDetail;
import com.example.laptop.db.model.CartDetailModel;
import com.example.laptop.db.model.CartModel;
import com.example.laptop.db.model.OrderDetailModel;
import com.example.laptop.db.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class OrderMapper implements EntityMapper<OrderModel, Order> {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Mapping(source = "orderDetails", target = "orderDetailModels", qualifiedByName = "toOrderDetailModel")
    public abstract OrderModel toModel(Order entity);


    @Named("toOrderDetailModel")
    public List<OrderDetailModel> toCartDetailModel(List<OrderDetail> orderDetails){
        return orderDetailMapper.toModel(orderDetails);
    }
}
