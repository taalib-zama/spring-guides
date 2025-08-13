package com.example.sampleProject1.demo.from.app.architecture.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {

    private Long paymentId;
    private double amount;
    private String currency;

}
