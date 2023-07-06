package com.nttdata.walletmicroservice.business;

import com.nttdata.walletmicroservice.entity.User;
import com.nttdata.walletmicroservice.events.Event;
import com.nttdata.walletmicroservice.events.EventType;
import com.nttdata.walletmicroservice.events.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Component
public class KafkaEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventProducer.class);

    @Autowired
    private KafkaTemplate<String, Event<?>> producer;

    public Mono<User> publish(User user) {

        UserCreatedEvent created = new UserCreatedEvent();
        created.setData(user);
        created.setId(UUID.randomUUID().toString());
        created.setType(EventType.CREATED);
        created.setDate(new Date());
        LOGGER.info("UserCreatedEvent -> " + created);
        this.producer.send("user-topic", created);
        return Mono.just(user);
    }
}
