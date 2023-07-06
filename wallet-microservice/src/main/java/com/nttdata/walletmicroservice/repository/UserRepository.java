package com.nttdata.walletmicroservice.repository;

import com.nttdata.walletmicroservice.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
