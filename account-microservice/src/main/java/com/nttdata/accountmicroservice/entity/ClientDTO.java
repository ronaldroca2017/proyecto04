package com.nttdata.accountmicroservice.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

    private String idClient;

    private String name;

    private String surnames;

    private String dni;

    private String clientType;
}
