package com.example.laptop.db.mapper;

import com.example.laptop.db.entity.User;
import com.example.laptop.db.model.UserModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {})
public abstract class UserMapper implements EntityMapper<UserModel, User> {
}
