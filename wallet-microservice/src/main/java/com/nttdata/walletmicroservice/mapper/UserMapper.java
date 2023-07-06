package com.nttdata.walletmicroservice.mapper;

import com.nttdata.walletmicroservice.entity.User;
import com.openapi.gen.springboot.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements EntityMapper<UserDTO, User>{

    @Override
    public User toEntity(UserDTO dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public UserDTO toDto(User entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
