package com.nttdata.clientmicroservice.mapper;

import com.nttdata.clientmicroservice.entity.Client;
import com.openapi.gen.springboot.dto.ClientDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class ClientMapper implements EntityMapper<ClientDTO, Client>{

    @Override
    public Client toEntity(ClientDTO dto) {
        Client entity= new Client();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public ClientDTO toDto(Client entity) {
        ClientDTO dto = new ClientDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
