package com.nttdata.accountmicroservice.api;

import com.nttdata.accountmicroservice.entity.Account;
import com.nttdata.accountmicroservice.response.ClientResponse;
import com.nttdata.accountmicroservice.response.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConsumeExternalApi {

    private static WebClient webClient;
    private static WebClient webClientProduct;

    public ConsumeExternalApi(){
        webClient = WebClient.create("http://localhost:8080");
        webClientProduct =  WebClient.create("http://localhost:8084");
    }


    public static Mono<ClientResponse> searchClientById(Account account){
        Mono<ClientResponse> monoClientResponse = webClient.get()
                .uri("/manage/client/{id}", account.getIdClient())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.empty())
                .bodyToMono(ClientResponse.class);
        return monoClientResponse;

    }

    public static Mono<ProductResponse> searchProductById(Account account){
        Mono<ProductResponse> monoProductResponse = webClientProduct.get()
                .uri("/manager/product/{id}", account.getIdProduct())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.empty())
                .bodyToMono(ProductResponse.class);

        return monoProductResponse;

    }


}
