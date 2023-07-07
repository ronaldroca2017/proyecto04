package com.nttdata.accountmicroservice.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ProductDTO {

    private String idProduct;

    private String description;

    private String productType;
}
