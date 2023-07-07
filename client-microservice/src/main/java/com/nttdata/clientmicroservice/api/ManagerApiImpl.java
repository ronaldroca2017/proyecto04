package com.nttdata.clientmicroservice.api;

import com.nttdata.clientmicroservice.business.ClientService;
import com.nttdata.clientmicroservice.mapper.ClientMapper;
import com.openapi.gen.springboot.api.ManageApi;
import com.openapi.gen.springboot.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


import static com.nttdata.clientmicroservice.util.constantes.Constantes.*;

@RestController
public class ManagerApiImpl implements ManageApi {


    @Autowired
    private ClientService clienteService;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> saveClient(Mono<ClientDTO> clientDTO, ServerWebExchange exchange) {

        Map<String, Object> response = new HashMap<>();

        return clienteService.saveClient(clientDTO.map(clientMapper::toEntity))
                .map(clientMapper::toDto)
                .map(client -> {
                    response.put(CLIENT_RESPONSE, client);
                    response.put(MESSAGE, MESSAGE_SAVE);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> updateClient(String id, Mono<ClientDTO> clientDTO, ServerWebExchange exchange) {
        Map<String, Object> response = new HashMap<>();

       return clienteService.updateClient(id, clientDTO.map(clientMapper::toEntity))
               .map(clientMapper::toDto)
               .map(client -> {
                   response.put(CLIENT_RESPONSE, client);
                   response.put(MESSAGE, MESSAGE_UPDATE);
                   return ResponseEntity.status(HttpStatus.CREATED).body(response);
               })
               .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<ClientDTO>>> getClients(ServerWebExchange exchange) {
        Flux<ClientDTO> fluxClientDto = clienteService.findAll().map(clientMapper::toDto);
        return Mono.just(fluxClientDto)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<ClientDTO>> getClientById(String id, ServerWebExchange exchange) {


        return clienteService.findById(id)
                .map(clientMapper::toDto)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
