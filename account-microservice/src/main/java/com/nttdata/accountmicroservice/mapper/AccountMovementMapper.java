package com.nttdata.accountmicroservice.mapper;

import com.nttdata.accountmicroservice.entity.AccountMovements;
import com.openapi.gen.springboot.dto.AccountMovementsDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountMovementMapper implements  EntityMapper<AccountMovementsDTO, AccountMovements>{


    @Override
    public AccountMovements toEntity(AccountMovementsDTO dto) {
        return null;
    }

    @Override
    public AccountMovementsDTO toDto(AccountMovements entity) {
        AccountMovementsDTO dto = new AccountMovementsDTO();
        BeanUtils.copyProperties(entity , dto);
        return dto;
    }
}
