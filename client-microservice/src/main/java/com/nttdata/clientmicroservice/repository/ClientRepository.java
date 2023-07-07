package com.nttdata.clientmicroservice.repository;

import com.nttdata.clientmicroservice.entity.Client;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    @Query("{'dni' : ?0}")
    Mono<Client> getClientByDocumentNumber(String dni);
}
