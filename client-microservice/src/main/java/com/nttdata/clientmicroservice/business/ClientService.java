package com.nttdata.clientmicroservice.business;

import com.nttdata.clientmicroservice.entity.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<Client> saveClient(Mono<Client> client);

    Mono<Client> updateClient(String id, Mono<Client> client);

    Flux<Client> findAll();

    Mono<Client> findById(String idClient);

    Mono<Client> getClientByDocumentNumber(String dni);

}
