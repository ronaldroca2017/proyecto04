package com.nttdata.clientmicroservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Document(collection = "clients")
public class Client {

    @EqualsAndHashCode.Include
    @Id
    private String idClient;

    @Field
    private String name;

    @Field
    private String surnames;

    @Field
    private String dni;

    @Field
    private String clientType;



}