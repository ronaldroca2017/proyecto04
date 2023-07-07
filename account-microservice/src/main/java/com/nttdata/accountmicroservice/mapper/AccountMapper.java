package com.nttdata.accountmicroservice.mapper;


import com.nttdata.accountmicroservice.entity.Account;
import com.nttdata.accountmicroservice.entity.AccountMovements;
import com.openapi.gen.springboot.dto.AccountDTO;
import com.openapi.gen.springboot.dto.AccountMovementsDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper implements EntityMapper<AccountDTO, Account> {

    @Override
    public Account toEntity(AccountDTO dto) {
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public AccountDTO toDto(Account entity) {
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Account toEntityAll(AccountDTO dto) {
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        AccountMovements m = new AccountMovements();
        List<AccountMovements> lstMovements = new ArrayList<>();
       // m.setId(dto.getMovements().get(0).getId());
        m.setQuantity(dto.getMovements().get(0).getQuantity());
        m.setPaymentDate(dto.getMovements().get(0).getPaymentDate());
        m.setDateTransaction(dto.getMovements().get(0).getDateTransaction());
        m.setPaymentStatus(dto.getMovements().get(0).getPaymentStatus());
        m.setTypeMovement(dto.getMovements().get(0).getTypeMovement());
        lstMovements.add(m);
        entity.setMovements(lstMovements);

        return entity;
    }

    public AccountDTO toDtoAll(Account entity) {
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(entity, dto);
        AccountMovementsDTO mDto = new AccountMovementsDTO();
        List<AccountMovementsDTO> lstMovementsDto = new ArrayList<>();
       // mDto.setId(entity.getMovements().get(0).getId());
        mDto.setQuantity(entity.getMovements().get(0).getQuantity());
        mDto.setPaymentDate(entity.getMovements().get(0).getPaymentDate());
        mDto.setDateTransaction(entity.getMovements().get(0).getDateTransaction());
        mDto.setPaymentStatus(entity.getMovements().get(0).getPaymentStatus());
        mDto.setTypeMovement(entity.getMovements().get(0).getTypeMovement());
        lstMovementsDto.add(mDto);
        dto.setMovements(lstMovementsDto);

        return dto;
    }
}
