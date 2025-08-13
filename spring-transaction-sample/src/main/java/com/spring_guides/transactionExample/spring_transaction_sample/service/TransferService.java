package com.spring_guides.transactionExample.spring_transaction_sample.service;

import com.spring_guides.transactionExample.spring_transaction_sample.model.Account;
import com.spring_guides.transactionExample.spring_transaction_sample.repo.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {
    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        accountRepository.withdraw(fromAccountId, amount);
        accountRepository.deposit(toAccountId, amount);
    }

    public List<Account> getAccountBalances(List<Long> accountIds) {
        List<Account> fetchedAccounts  =  accountRepository.findByIdIn(accountIds);
        return fetchedAccounts;
    }
}
