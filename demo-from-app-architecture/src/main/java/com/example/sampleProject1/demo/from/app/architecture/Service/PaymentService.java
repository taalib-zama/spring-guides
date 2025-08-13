package com.example.sampleProject1.demo.from.app.architecture.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public PaymentResponseDTO getPaymentDetailsById(PaymentRequestDTO paymentRequestDTO) {
        // Logic to fetch payment details by ID
        // This is a placeholder for the actual implementation
        PaymentEntity paymentModel  = paymentRepository.getPaymentDetailsById(paymentRequestDTO);


        // Convert the PaymentEntity to PaymentResponseDTO
        PaymentResponseDTO paymentResponseDTO = mapModelToResponseDTO(paymentModel);
        return paymentResponseDTO;
    }

    private PaymentResponseDTO mapModelToResponseDTO(PaymentEntity paymentModel) {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setPaymentId(paymentModel.getId());
        paymentResponseDTO.setAmount(paymentModel.getPaymentAmount());
        paymentResponseDTO.setCurrency(paymentModel.getPaymentCurrency());
        return paymentResponseDTO;
    }


}
