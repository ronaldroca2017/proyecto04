package com.nttdata.walletmicroservice.api;

import com.nttdata.walletmicroservice.response.ClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConsumeExternalApi {
    private static WebClient webClient;


    public ConsumeExternalApi(){
        webClient = WebClient.create("http://localhost:8080");
    }

    public static Mono<ClientResponse> searchClientById(String dni){
        Mono<ClientResponse> monoClientResponse = webClient.get()
                .uri("/manage/clientByDocumentNumber/{id}", dni)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.empty())
                .bodyToMono(ClientResponse.class);
        return monoClientResponse;
    }

}
