package com.nttdata.accountmicroservice.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private String idProduct;

    private String description;

    private String productType;

}
