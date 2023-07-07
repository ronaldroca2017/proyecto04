package com.nttdata.walletmicroservice.repository;

import com.nttdata.walletmicroservice.entity.Wallet;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface WalletRepository extends ReactiveMongoRepository<Wallet, String> {

    @Query("{'codeWallet' : ?0}")
    public Mono<Wallet> getWallet(String codeWallet);
}
