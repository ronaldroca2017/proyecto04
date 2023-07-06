package com.nttdata.walletmicroservice.api;

import com.nttdata.walletmicroservice.business.UserService;
import com.nttdata.walletmicroservice.mapper.UserMapper;
import com.openapi.gen.springboot.api.ManagerApi;
import com.openapi.gen.springboot.dto.UserDTO;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ManageApiImpl implements ManagerApi {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper  userMapper;

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> saveWalletUser(Mono<UserDTO> userDTO, ServerWebExchange exchange) {
        Map<String, Object> response = new HashMap<>();

        return userService.saveUser(
                userDTO.map(userMapper::toEntity))
                .map(userMapper::toDto)
                .map(user -> {
                    response.put("user" , user);
                    response.put("message", "usuario registrado");
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }
}
