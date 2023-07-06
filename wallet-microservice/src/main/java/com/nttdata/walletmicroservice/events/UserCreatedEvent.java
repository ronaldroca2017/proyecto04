package com.nttdata.walletmicroservice.events;


import com.nttdata.walletmicroservice.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent  extends Event<User>{
}
