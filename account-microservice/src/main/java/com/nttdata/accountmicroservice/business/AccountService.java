package com.nttdata.accountmicroservice.business;

import com.nttdata.accountmicroservice.entity.Account;
import com.nttdata.accountmicroservice.entity.AccountMovements;
import com.nttdata.accountmicroservice.entity.ProductDTO;
import com.nttdata.accountmicroservice.response.ApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {



    Mono<Account> saveAccount(Mono<Account> account);

    Mono<Account> update(Mono<Account> account);

    Flux<Account> findAll();

    Mono<Account> findById(String id);

    Flux<Account> findAccountByIdClient(String idClient);

    Flux<Account> findAccountByIdProduct(String idProduct);

    Flux<Account> consultAvailableBalances(String idClient);

    Flux<AccountMovements> consultClientMovement(String idClient, String idProduct);

    Flux<ProductDTO> clientSummaryOfAllProducts(String idClient);

    Mono<Boolean> checkPaymentStatus(String idClient , String idProduct);

}
