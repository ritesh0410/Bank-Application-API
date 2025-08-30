package com.IndraSoftech.Bank_App.service;

import com.IndraSoftech.Bank_App.dto.AccountDto;

import java.util.List;


public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id,double amount);
    List<AccountDto> getAllAccount();

    void deleteAccount(Long id);

}
