package com.nttdata.accountmicroservice.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {

    private String idClient;


    private String name;


    private String surnames;


    private String dni;

    private String clientType;
}
