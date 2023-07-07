package com.nttdata.walletmicroservice.repository;

import com.nttdata.walletmicroservice.entity.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    @Query("{'cellPhoneNumber' : ?0, 'identificationDocumentNumber' : ?1}")
    Mono<User> getUser(String cellPhoneNumber, String identificationDocumentNumber);
}
