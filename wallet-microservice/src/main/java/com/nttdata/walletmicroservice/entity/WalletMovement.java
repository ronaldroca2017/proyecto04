package com.nttdata.walletmicroservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WalletMovement {

    @Field
    private Double amount;

    @Field
    private String typeMovement;
}
