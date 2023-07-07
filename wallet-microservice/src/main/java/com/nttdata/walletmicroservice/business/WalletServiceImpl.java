package com.nttdata.walletmicroservice.business;

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

/*
    @Override
    public Mono<Wallet> registerWallet(Mono<Wallet> wallet) {

        return wallet.flatMap(w -> {
            w.setAvailableBalance(w.getLstWalletMovement().get(0).getAmount());
           return walletRepository.save(w);
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

                        return walletRepository.save(w);
                    });

        });

    }*/

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
                       /* walletBD.getLstWalletMovement()
                                .stream().map(m -> {
                                    return w.getLstWalletMovement().add(m);
                                });*/

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

    }

    @Override
    public Mono<Wallet> getWallet(String codeWallet) {
        return walletRepository.getWallet(codeWallet);
    }

    @Override
    public Mono<User> getUser(String cellPhoneNumber, String identificationDocumentNumber) {
        return userRepository.getUser(cellPhoneNumber, identificationDocumentNumber);
    }

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
    }
}
