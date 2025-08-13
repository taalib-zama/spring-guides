package com.example.sampleProject1.demo.from.app.architecture.Service;

import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    public PaymentEntity getPaymentDetailsById(PaymentRequestDTO paymentRequestDTO) {
        PaymentEntity paymentEntity = executeQuery(paymentRequestDTO);
        return paymentEntity;
    }

    private PaymentEntity executeQuery(PaymentRequestDTO paymentRequestDTO) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(paymentRequestDTO.getPaymentId());
        paymentEntity.setPaymentAmount(100.0); // Example amount
        paymentEntity.setPaymentCurrency("USD"); // Example currency
        return paymentEntity;
    }
}
