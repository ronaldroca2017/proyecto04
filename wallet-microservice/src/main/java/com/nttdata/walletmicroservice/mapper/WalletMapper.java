package com.nttdata.walletmicroservice.mapper;

import com.nttdata.walletmicroservice.entity.Wallet;
import com.nttdata.walletmicroservice.entity.WalletMovement;
import com.openapi.gen.springboot.dto.WalletDTO;
import com.openapi.gen.springboot.dto.WalletMovementsDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WalletMapper implements EntityMapper<WalletDTO, Wallet> {

    @Override
    public Wallet toEntity(WalletDTO dto) {
        Wallet entity = new Wallet();
        BeanUtils.copyProperties(dto, entity);

        List<WalletMovement> lstWalletMovement = new ArrayList<>();
        WalletMovement walletMovement = new WalletMovement();
        walletMovement.setAmount(dto.getLstWalletMovement().get(0).getAmount());
        walletMovement.setTypeMovement(dto.getLstWalletMovement().get(0).getTypeMovement());
        lstWalletMovement.add(walletMovement);
        entity.setLstWalletMovement(lstWalletMovement);

        return entity;
    }

    @Override
    public WalletDTO toDto(Wallet entity) {
        WalletDTO dto = new WalletDTO();
        BeanUtils.copyProperties(entity, dto);

        List<WalletMovementsDTO> lstWalletMovementDTO = new ArrayList<>();
        WalletMovementsDTO walletMovementDTO = new WalletMovementsDTO();
        walletMovementDTO.setAmount(entity.getLstWalletMovement().get(0).getAmount());
        walletMovementDTO.setTypeMovement(entity.getLstWalletMovement().get(0).getTypeMovement());
        lstWalletMovementDTO.add(walletMovementDTO);
        dto.setLstWalletMovement(lstWalletMovementDTO);

        return dto;
    }
}
