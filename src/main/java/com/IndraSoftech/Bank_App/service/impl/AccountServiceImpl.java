package com.IndraSoftech.Bank_App.service.impl;

import com.IndraSoftech.Bank_App.dto.AccountDto;
import com.IndraSoftech.Bank_App.entity.Account;
import com.IndraSoftech.Bank_App.exception.AccountException;
import com.IndraSoftech.Bank_App.mapper.AccountMapper;
import com.IndraSoftech.Bank_App.repository.AccountRepository;
import com.IndraSoftech.Bank_App.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        double total=account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        if(amount>account.getBalance())
            throw  new RuntimeException("Insufficient amount");
        double remainAmount=account.getBalance()-amount;
        account.setBalance(remainAmount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new AccountException("Account does not exist"));
        accountRepository.deleteById(id);
    }


}
