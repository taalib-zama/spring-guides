package com.spring_guides.transactionExample.spring_transaction_sample.repo;

import com.spring_guides.transactionExample.spring_transaction_sample.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("update Account set balance = balance + :amount where id = :id")
    void deposit(@Param("id") Long id, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("update Account set balance = balance - :amount where id = :id")
    void withdraw(@Param("id") Long id, @Param("amount") BigDecimal amount);

    List<Account> findByIdIn(List<Long> accountIds);
}
