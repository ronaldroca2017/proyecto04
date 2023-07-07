package com.nttdata.walletmicroservice.business;

import com.nttdata.walletmicroservice.api.ConsumeExternalApi;
import com.nttdata.walletmicroservice.entity.User;
import com.nttdata.walletmicroservice.entity.Wallet;
import com.nttdata.walletmicroservice.repository.UserRepository;
import com.nttdata.walletmicroservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Mono<Wallet> registerWallet(Mono<Wallet> wallet) {

        return wallet.flatMap(w -> {
            w.setAvailableBalance(w.getLstWalletMovement().get(0).getAmount());
           return walletRepository.save(w);
        });

    }
/*
    @Override
    public Mono<Wallet> registerWallet(Mono<Wallet> wallet) {

        return wallet.flatMap(w -> {
           return getWallet(w.getCodeWallet())
                    .map(walletBD -> {
                        if(w.getLstWalletMovement().get(0).getTypeMovement().equals("DEPOSITO")){
                            w.setAvailableBalance(walletBD.getAvailableBalance() + w.getLstWalletMovement().get(0).getAmount());
                        }else{
                            w.setAvailableBalance(walletBD.getAvailableBalance() - w.getLstWalletMovement().get(0).getAmount());
                        }
                        w.getLstWalletMovement().addAll(walletBD.getLstWalletMovement());

                        return Mono.just(w);
                    }).flatMap(walletFinal -> {

                        return walletRepository.save(w);
                    });

        });

    }*/
/*
    @Override
    public Mono<Wallet> registerWallet(Mono<Wallet> wallet) {

        return wallet.flatMap(w -> {
            return getWallet(w.getCodeWallet())
                    .map(walletBD -> {
                        if(w.getLstWalletMovement().get(0).getTypeMovement().equals("DEPOSITO")){
                            w.setAvailableBalance(walletBD.getAvailableBalance() + w.getLstWalletMovement().get(0).getAmount());
                        }else{
                            w.setAvailableBalance(walletBD.getAvailableBalance() - w.getLstWalletMovement().get(0).getAmount());
                        }
                        w.getLstWalletMovement().addAll(walletBD.getLstWalletMovement());

                        return Mono.just(w);
                    }).flatMap(walletFinal -> {
                        getUser("", "")
                                .flatMap(user -> {
                                    w.setIdUser(user.getId());
                                    return Mono.just(w);
                                });
                        return walletRepository.save(w);
                    });

        });

    }*/

    @Override
    public Mono<Wallet> getWallet(String codeWallet) {
        return walletRepository.getWallet(codeWallet);
    }

    @Override
    public Mono<User> getUser(String cellPhoneNumber, String identificationDocumentNumber) {
        return userRepository.getUser(cellPhoneNumber, identificationDocumentNumber);
    }


    /*
    @Override
    public Mono<Wallet> walletTransaction(String cellPhoneNumber, String identificationDocumentNumber, Mono<Wallet> wallet) {
        return wallet.flatMap(w -> {
            return getWallet(w.getCodeWallet())
                    .map(walletBD -> {
                        if(w.getLstWalletMovement().get(0).getTypeMovement().equals("DEPOSITO")){
                            w.setAvailableBalance(walletBD.getAvailableBalance() + w.getLstWalletMovement().get(0).getAmount());
                        }else{
                            w.setAvailableBalance(walletBD.getAvailableBalance() - w.getLstWalletMovement().get(0).getAmount());
                        }
                        w.getLstWalletMovement().addAll(walletBD.getLstWalletMovement());

                        return Mono.just(w);
                    })
                    .flatMap(walletFinal -> {
                        return getUser(cellPhoneNumber, identificationDocumentNumber)
                                .flatMap(user -> {
                                    w.setIdUser(user.getId());
                                    return Mono.just(w);
                                });

                    })
                    .flatMap(t -> walletRepository.save(w));


        });
    }*/

    @Override
    public Mono<Wallet> walletTransaction(String cellPhoneNumber, String identificationDocumentNumber, Mono<Wallet> wallet) {
        return wallet.flatMap(walletRequest -> {
            return getWallet(walletRequest.getCodeWallet())
                    .map(walletBD -> {
                        if(walletRequest.getLstWalletMovement().get(0).getTypeMovement().equals("DEPOSITO")){
                            walletRequest.setAvailableBalance(walletBD.getAvailableBalance() + walletRequest.getLstWalletMovement().get(0).getAmount());
                        }else{
                            walletRequest.setAvailableBalance(walletBD.getAvailableBalance() - walletRequest.getLstWalletMovement().get(0).getAmount());
                        }
                        walletRequest.getLstWalletMovement().addAll(walletBD.getLstWalletMovement());

                        return Mono.just(walletRequest);
                    })
                    .flatMap(userExist -> {
                        return getUser(cellPhoneNumber, identificationDocumentNumber)
                                .flatMap(user -> {
                                    walletRequest.setIdUser(user.getId());
                                    return Mono.just(walletRequest);
                                });

                    })
                   /* .flatMap(clientExist -> {
                        return ConsumeExternalApi.searchClientById(identificationDocumentNumber)
                                .flatMap(client -> {
                                    walletRequest.setIdClient(client.getIdClient());
                                    return Mono.just(walletRequest);
                                });

                    })*/
                    .flatMap(t -> walletRepository.save(walletRequest));


        });
    }
}
