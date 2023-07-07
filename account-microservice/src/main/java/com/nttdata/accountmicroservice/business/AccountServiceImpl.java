package com.nttdata.accountmicroservice.business;

import com.nttdata.accountmicroservice.api.ConsumeExternalApi;
import com.nttdata.accountmicroservice.entity.Account;
import com.nttdata.accountmicroservice.entity.AccountMovements;
import com.nttdata.accountmicroservice.entity.ClientDTO;
import com.nttdata.accountmicroservice.entity.ProductDTO;
import com.nttdata.accountmicroservice.exception.AccountException;
import com.nttdata.accountmicroservice.exception.ClientException;
import com.nttdata.accountmicroservice.exception.ProductException;
import com.nttdata.accountmicroservice.repository.AccountRepository;
import com.nttdata.accountmicroservice.response.ApiResponse;
import com.nttdata.accountmicroservice.response.ClientResponse;
import com.nttdata.accountmicroservice.response.ProductResponse;
import com.nttdata.accountmicroservice.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.nttdata.accountmicroservice.util.Constantes.*;


@Service
public class AccountServiceImpl implements  AccountService{


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Mono<Account> saveAccount(Mono<Account>  account){

      return account.flatMap(act -> {
            return  ConsumeExternalApi.searchClientById(act)
                    .switchIfEmpty(Mono.error(new ClientException(MESSAGE_CLIENT_NOT_REGISTER)))
                    .flatMap(a -> {
                        if(a.getClientType().equals(PERSON_TYPE_PERSONAL)) {
                            return clientPersonal(act);
                        }else if(a.getClientType().equals(PERSON_TYPE_BUSINESS)){
                            return clientBusiness(act);
                        } else if (a.getClientType().equals(PERSON_VIP_TYPE_PERSONAL)) {
                                if(act.getInitialAmount()>=500){
                                    findById(act.getIdClient()).flatMap(acts -> {
                                        if(acts.getProductType().equals(TARJETA_CREDITO_PERSONAL)){
                                            return account.flatMap(accountRepository::save);
                                        }
                                        return Mono.error(new Exception("No cuenta con una tarjeta de crédito"));
                                    });

                                }else{
                                    return Mono.error(new Exception("El monto para aperturar una cuenta debe ser igual o mayor a 500"));
                                }
                        }else if (a.getClientType().equals(PERSON_MYPE_TYPE_BUSINESS)) {

                                findById(act.getIdClient()).flatMap(acts -> {
                                    if(acts.getProductType().equals(TARJETA_CREDITO_EMPRESARIAL)
                                        && acts.getProductType().equals(CURRENT_ACCOUNT)){
                                        return account.flatMap(accountRepository::save);
                                    }else{
                                        return Mono.error(new Exception("No cuenta con una tarjeta de crédito, ni tampoco con una cuenta corriente" ));
                                    }
                                });

                        }
                        return Mono.error(new Exception("error general"));
            });
        });
    }

    @Override
    public Mono<Account> update(Mono<Account> account) {

    return account.flatMap(act -> {
       return ConsumeExternalApi.searchProductById(act)
               .switchIfEmpty(Mono.error(new ProductException(MESSAGE_PRODUCT_NOT_REGISTER)))
               .flatMap(p -> {
                   if(p.getDescription().equals(CUENTAS_BANCARIAS)){
                       if(act.getMovements().get(0).getTypeMovement().equals(DEPOSITO)){
                            findAll()
                                    .count()
                                    .flatMap(c -> {
                                        if(c.intValue() <= MAXIMO_TRANSACCIONES){
                                            act.setAmountUpdated(act.getAmountUpdated() +  act.getMovements().get(0).getQuantity());
                                        }else{
                                            act.setAmountUpdated(act.getAmountUpdated() +  act.getMovements().get(0).getQuantity());
                                            act.setCommission(COMISION);
                                        }
                                        return null;
                                    });

                       } else if (act.getMovements().get(0).getTypeMovement().equals(RETIRO)) {
                           findAll()
                                   .count()
                                   .flatMap(c -> {
                               if(c.intValue() <= MAXIMO_TRANSACCIONES){
                                   act.setAmountUpdated(act.getAmountUpdated() -  act.getMovements().get(0).getQuantity());
                               }else{
                                   act.setAmountUpdated(act.getAmountUpdated() -  act.getMovements().get(0).getQuantity());
                                   act.setCommission(COMISION);
                               }
                               return null;
                           });

                       }
                   }else if(p.getDescription().equals(CREDITOS)){
                       if(act.getMovements().get(0).getTypeMovement().equals(PAGO)){
                           act.setCreditAmountUpdated(act.getCreditAmountUpdated() +  act.getMovements().get(0).getQuantity());
                       }else if (act.getMovements().get(0).getTypeMovement().equals(CONSUMO)){
                           act.setCreditAmountUpdated(act.getCreditAmountUpdated() -  act.getMovements().get(0).getQuantity());
                       }
                   }
                   return accountRepository.save(act);
               });
    });


    }

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public Flux<Account> findAccountByIdClient(String idClient) {
        return accountRepository.findAccountByIdClient(idClient);
    }

    @Override
    public Flux<Account> findAccountByIdProduct(String idProduct) {
        return accountRepository.findAccountByIdProduct(idProduct);
    }

    @Override
    public Flux<Account> consultAvailableBalances(String idClient) {
        return accountRepository.consultAvailableBalances(idClient);
    }

    @Override
    public Flux<AccountMovements> consultClientMovement(String idClient, String idProduct) {
        return accountRepository.consultClientMovement(idClient, idProduct);
    }




    private Mono<Account> clientPersonal(Account account){

        return checkPaymentStatus(account.getIdClient(), account.getIdProduct())
                .flatMap(checkStatus -> {
                    if(checkStatus){
                       return  Mono.from(findAccountByIdClient(account.getIdClient())
                                .map(a -> {
                                    if (a != null && a.getProductType() != null) {
                                        if (!a.getProductType().equals(account.getProductType())) {
                                            accountRepository.save(account).subscribe();
                                        }
                                        else
                                            Mono.error(new AccountException(ACCOUNT_PRODUCTYPE_EXISTS));
                                    }
                                    return account;
                                })
                                .switchIfEmpty(Mono.defer(() -> accountRepository.save(account))));
                    }else{
                       return Mono.error(new AccountException(CHECK_STATUS_INVALID));
                    }
                });

        }



    private Mono<Account> clientBusiness(Account account){

    if(account.getProductType().equals(Constantes.CURRENT_ACCOUNT)){
          accountRepository.save(account).subscribe();
    }else{
        return Mono.error(new AccountException(MESSAGE_ACCOUNT_CLIENTE_EMPRESARIAL));
    }

        return Mono.just(account);
    }

    private boolean isClientBusinessValidationCurrentAccount(Account account){
        return account.getProductType().equals(Constantes.CURRENT_ACCOUNT);
    }

    // PUNTO 01
    @Override
    public Flux<ProductDTO> clientSummaryOfAllProducts(String idClient) {
        return accountRepository.findAccountByIdClient(idClient)
                .switchIfEmpty(Mono.error(new AccountException(MESSAGE_CLIENT_NOT_ACCOUNT)))
                .flatMap(a ->  {
                    return ConsumeExternalApi.searchProductById(a);
                }).map(productResponse -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setIdProduct(productResponse.getIdProduct());
                    productDTO.setDescription(productResponse.getDescription());
                    productDTO.setProductType(productResponse.getProductType());

                    return productDTO;
                });
    }

    // PUNTO 02
    @Override
    public Mono<Boolean> checkPaymentStatus(String idClient, String idProduct) {
        return Mono.from(accountRepository.findAccountByIdClient(idClient)
                .filter(accountBD -> {
                         return accountBD.getProductType().equals(CREDITO_PERSONAL);
                })
                .flatMap(accountEntity -> {

                    return accountRepository.consultClientMovement(idClient, idProduct)
                            .flatMap(movements -> {
                                if (movements.getPaymentStatus().equals(CREDITO_STATUS_PENDIENTE)) {
                                    return Mono.just(true);
                                }
                                return Mono.just(false);
                            });
                })
               .switchIfEmpty(Mono.just(true)));

    }




}
