package com.nttdata.walletmicroservice.api;

import com.nttdata.walletmicroservice.business.UserService;
import com.nttdata.walletmicroservice.business.WalletService;
import com.nttdata.walletmicroservice.entity.User;
import com.nttdata.walletmicroservice.mapper.UserMapper;
import com.nttdata.walletmicroservice.mapper.WalletMapper;
import com.openapi.gen.springboot.api.ManagerApi;
import com.openapi.gen.springboot.dto.UserDTO;
import com.openapi.gen.springboot.dto.WalletDTO;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ManageApiImpl implements ManagerApi {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserMapper  userMapper;

    @Autowired
    WalletMapper walletMapper;

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

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> saveWallet(Mono<WalletDTO> walletDTO, ServerWebExchange exchange) {
        Map<String, Object> response = new HashMap<>();

        return walletService.registerWallet(
                walletDTO.map(walletMapper::toEntity))
                .map(walletMapper::toDto)
                .map(w -> {
                    response.put("wallet" , w);
                    response.put("message", "wallet registrado");
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });

    }

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> walletTransaction(String cellPhoneNumber, String identificationDocumentNumber, Mono<WalletDTO> walletDTO, ServerWebExchange exchange) {
        Map<String, Object> response = new HashMap<>();

        return walletService.walletTransaction(cellPhoneNumber, identificationDocumentNumber,
                        walletDTO.map(walletMapper::toEntity))
                .map(walletMapper::toDto)
                .map(w -> {
                    response.put("wallet" , w);
                    response.put("message", "wallet transaction sucess");
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }

    @GetMapping(value = "/buscarUser/{cellPhoneNumber}/{identificationDocumentNumber}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable("cellPhoneNumber")  String cellPhoneNumber,
                                              @PathVariable("identificationDocumentNumber")  String identificationDocumentNumber){
        return walletService.getUser(cellPhoneNumber, identificationDocumentNumber)
                .map(x -> ResponseEntity.ok().body(x));
    }

}
