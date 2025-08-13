package com.spring_guides.transactionExample.spring_transaction_sample.Controller;

import com.spring_guides.transactionExample.spring_transaction_sample.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final com.spring_guides.transactionExample.spring_transaction_sample.service.TransferService transferService;

    public Controller(com.spring_guides.transactionExample.spring_transaction_sample.service.TransferService transferService) {
        this.transferService = transferService;
    }

    @org.springframework.web.bind.annotation.PostMapping("/transfer")
    public org.springframework.http.ResponseEntity<String> transfer(
            @org.springframework.web.bind.annotation.RequestParam Long fromAccountId,
            @org.springframework.web.bind.annotation.RequestParam Long toAccountId,
            @org.springframework.web.bind.annotation.RequestParam java.math.BigDecimal amount) {
        try {
            transferService.transfer(fromAccountId, toAccountId, amount);
            return org.springframework.http.ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Transfer failed: " + e.getMessage());
        }
    }

        //get api to check balance (of multiple accounts) before or after transaction.
        @GetMapping("/accounts/balance")
        public ResponseEntity<List<Account>> getAccountBalances(@RequestParam List<Long> accountIds) {
            transferService.getAccountBalances(accountIds);
            return ResponseEntity.status(HttpStatus.OK).body(
                    transferService.getAccountBalances(accountIds)
            );
        }

}
