package com.nttdata.walletmicroservice.business;


import com.nttdata.walletmicroservice.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<User> saveUser(Mono<User> walletUser);
}
