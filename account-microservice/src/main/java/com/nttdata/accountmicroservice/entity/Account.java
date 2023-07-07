package com.nttdata.accountmicroservice.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Document(collection = "accounts")
public class Account {
    @EqualsAndHashCode.Include
    @Id
    private String id;

    @Field
    private String accountNumber;

    @Field
    private Double initialAmount;

    @Field
    private Double amountUpdated;

    @Field
    private Double creditLimit;

    @Field
    private Double creditAmountUpdated;

    @Field
    private Double commission;

    @Field(name = "idClient")
    private String idClient;

    @Field(name = "idProduct")
    private String idProduct;

    @Field
    private String productType;

    @Field
    private List<AccountMovements> movements;



}