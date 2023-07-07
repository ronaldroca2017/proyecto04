package com.nttdata.walletmicroservice.business;

import com.nttdata.walletmicroservice.entity.User;
import com.nttdata.walletmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaEventProducer kafkaEventProducer;

    @Override
    public Mono<User> saveUser(Mono<User> walletUser) {

        return walletUser
                .flatMap(u -> {
                    u.setId(UUID.randomUUID().toString());
                    return  kafkaEventProducer.publish(u);
                })
                .flatMap(user -> userRepository.save(user));
    }



}
