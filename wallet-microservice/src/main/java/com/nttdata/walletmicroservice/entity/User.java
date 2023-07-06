package com.nttdata.walletmicroservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Document(collection = "wallterUsers")
public class User {

    @Id
    private String id;

    private String identificationDocumentNumber;

    private String cellPhoneNumber;

    private String cellPhoneImei;

    private String email;
}
