package com.nttdata.walletmicroservice.business;

import com.nttdata.walletmicroservice.entity.User;
import com.nttdata.walletmicroservice.entity.Wallet;
import reactor.core.publisher.Mono;

public interface WalletService {

    public Mono<Wallet> registerWallet(Mono<Wallet> wallet);

    public Mono<Wallet> getWallet(String codeWallet);

    public Mono<User> getUser(String cellPhoneNumber, String identificationDocumentNumber);

    public Mono<Wallet> walletTransaction(String cellPhoneNumber, String identificationDocumentNumber,  Mono<Wallet> wallet);
}
