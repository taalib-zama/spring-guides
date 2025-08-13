package com.example.sampleProject1.demo.from.app.architecture.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    private Long id;
    private double paymentAmount;
    private String paymentCurrency;
    private String userEmail;


}
