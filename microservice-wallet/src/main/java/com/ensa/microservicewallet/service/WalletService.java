package com.ensa.microservicewallet.service;



import com.ensa.microservicewallet.Entity.Wallet;
import com.ensa.microservicewallet.exception.WalletNotFoundException;

import java.util.List;

public interface WalletService {

    public Wallet CreateWallet(Wallet wallet);
    public Wallet UpdateWalletBalance(Long Id,Float Solde) throws WalletNotFoundException;
    public Wallet findWalletByWallet_Number(String Wallet_Number) throws WalletNotFoundException;

//    public Wallet findWalletByClientId(Long ClientId) throws WalletNotFoundException;
    public List<Wallet> findAllWallets();
    public void deleteWallet(Long id);

    public Wallet debit_balance(Long id,Float Amount) throws WalletNotFoundException;
    public Wallet credit_balance(Long id,Float Amount) throws WalletNotFoundException;

    Wallet debitBalance(Long id, Float amount);
    Wallet creditBalance(Long id, Float amount);

//    Float findWalletBalanceByClientId(Long clientId);
}
