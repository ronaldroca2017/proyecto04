package com.nttdata.walletmicroservice.entity;


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

@Document(collection = "wallter")
public class Wallet {

    @Id
    private String id;

    private String codeWallet;

    private Double availableBalance;

    private String idUser;

    private String idClient;

    private List<WalletMovement> lstWalletMovement;
}
