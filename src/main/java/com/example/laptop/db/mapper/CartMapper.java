package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.Cart;
import com.example.laptop.db.entity.CartDetail;
import com.example.laptop.db.model.CartDetailModel;
import com.example.laptop.db.model.CartModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class CartMapper implements   EntityMapper<CartModel, Cart> {

    @Autowired
    private CartDetailMapper cartDetailMapper;

    @Mapping(source = "cartDetails", target = "cartDetailModels", qualifiedByName = "toCartDetailModel")
    public abstract CartModel toModel(Cart entity);

    @Named("toCartDetailModel")
    public List<CartDetailModel> toCartDetailModel(List<CartDetail> cartDetails){
        return cartDetailMapper.toModel(cartDetails);
    }
}