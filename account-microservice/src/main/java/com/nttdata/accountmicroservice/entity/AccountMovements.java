package com.nttdata.accountmicroservice.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class AccountMovements {

    @Field
    private Double quantity;

    @Field
    private LocalDate dateTransaction;

    @Field
    private LocalDate paymentDate;

    @Field
    private String paymentStatus;

    @Field
    private String typeMovement;


}

