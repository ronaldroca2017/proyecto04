package com.nttdata.clientmicroservice.business;

import com.nttdata.clientmicroservice.entity.Client;
import com.nttdata.clientmicroservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Mono<Client> saveClient(Mono<Client> client) {
        return client.flatMap(clientRepository::save);
    }

    @Override
    public Mono<Client> updateClient(String id, Mono<Client> client) {
        return clientRepository.findById(id)
                .flatMap(c -> client)
                .doOnNext(e -> e.setIdClient(id))
                .flatMap(clientRepository::save);
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> findById(String idClient) {
        return clientRepository.findById(idClient);
    }
}
