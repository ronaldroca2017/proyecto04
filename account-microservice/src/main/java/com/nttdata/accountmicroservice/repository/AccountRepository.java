package com.nttdata.accountmicroservice.repository;

import com.nttdata.accountmicroservice.entity.Account;
import com.nttdata.accountmicroservice.entity.AccountMovements;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    @Query("{'idClient' : ?0}")
    Flux<Account> findAccountByIdClient(String idClient);

    @Query("{'idProduct' : ?0}")
    Flux<Account> findAccountByIdProduct(String idProduct);

    @Query("{'idClient' : ?0}")
    Flux<Account> consultAvailableBalances(String idClient);

    @Query("{'idClient' : ?0, 'idProducto' : ?1}")
    Flux<AccountMovements> consultClientMovement(String idClient, String idProducto);


}
