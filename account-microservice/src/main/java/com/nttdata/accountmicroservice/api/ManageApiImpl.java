package com.nttdata.accountmicroservice.api;

import com.nttdata.accountmicroservice.business.AccountService;
import com.nttdata.accountmicroservice.entity.ProductDTO;
import com.nttdata.accountmicroservice.mapper.AccountMapper;
import com.nttdata.accountmicroservice.mapper.AccountMovementMapper;
import com.openapi.gen.springboot.api.ManagerApi;
import com.openapi.gen.springboot.dto.AccountDTO;
import com.openapi.gen.springboot.dto.AccountMovementsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@RestController
public class ManageApiImpl implements ManagerApi {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountMovementMapper accountMovementMapper;

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> saveAccount(Mono<AccountDTO> accountDTO, ServerWebExchange exchange) {

        Map<String, Object> response = new HashMap<>();

        return accountService.saveAccount(accountDTO.map(accountMapper::toEntity))
                .map(accountMapper::toDto)
                .map(account -> {
                    response.put("cuenta", account);
                    response.put("message", "mensaje exitoso");
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });

    }

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> accountMovements(String id, Mono<AccountDTO> accountDTO, ServerWebExchange exchange) {
        Map<String, Object> response = new HashMap<>();

        return accountService.update(accountDTO.map(accountMapper::toEntityAll))
                .map(accountMapper::toDtoAll)
                .map(account -> {
                    response.put("cuenta", account);
                    response.put("message", "mensaje exitoso");
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }


    @Override
    public Mono<ResponseEntity<Flux<AccountDTO>>> getAccounts(ServerWebExchange exchange) {

        Flux<AccountDTO> fluxAccountDto = accountService.findAll().map(accountMapper::toDto);
        return Mono.just(fluxAccountDto)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<AccountDTO>>> getConsultAvailableBalances(String idClient, ServerWebExchange exchange) {

        return Mono.just(accountService.consultAvailableBalances(idClient).map(accountMapper::toDto))
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<AccountMovementsDTO>>> getConsultClientMovement(String idClient, String idProduct, ServerWebExchange exchange) {
        return Mono.just(accountService.consultClientMovement(idClient, idProduct).map(accountMovementMapper::toDto))
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping(value = "/sumary/{idClient}")
    public Mono<ResponseEntity<Flux<ProductDTO>>> clientSummaryOfAllProducts(@PathVariable("idClient")  String idClient){
        Flux<ProductDTO> fluxPrdoctDTO =  accountService.clientSummaryOfAllProducts(idClient);
            return Mono.just(ResponseEntity.ok().
                    body(fluxPrdoctDTO))
                    .defaultIfEmpty(ResponseEntity.noContent().build());
    }


}


