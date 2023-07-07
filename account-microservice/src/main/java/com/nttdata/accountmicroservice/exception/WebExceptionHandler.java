package com.nttdata.accountmicroservice.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Component
@Order(-1)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {


    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                               ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse); //req -> renderErrorResponse(req)
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        Map<String, Object> generalError = getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults());
        Map<String, Object> customError = new HashMap<>();

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        String statusCode = String.valueOf(generalError.get("status"));
        Throwable error = getError(serverRequest);

        if(error instanceof ClientException){
            customError.put("message", error.getMessage());
            customError.put("status", "400");
            status = HttpStatus.BAD_REQUEST;
        } else if(error instanceof AccountException){
            customError.put("message", error.getMessage());
            customError.put("status", "400");
            status = HttpStatus.BAD_REQUEST;
        }


        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(customError));

    }
}


