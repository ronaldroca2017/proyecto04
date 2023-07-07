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

@Document(collection = "cardsDebit")
public class CardDebit {
    @EqualsAndHashCode.Include
    @Id
    private String id;

    @Field
    private String numberCard;

    @Field
    private String expirationDate;

    @Field
    private String cvv;

}
