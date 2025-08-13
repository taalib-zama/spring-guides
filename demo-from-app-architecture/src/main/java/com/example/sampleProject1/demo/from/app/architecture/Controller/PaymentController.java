package com.example.sampleProject1.demo.from.app.architecture.Controller;

import com.example.sampleProject1.demo.from.app.architecture.Service.PaymentRequestDTO;
import com.example.sampleProject1.demo.from.app.architecture.Service.PaymentResponseDTO;
import com.example.sampleProject1.demo.from.app.architecture.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {

        //map incoming data to internal request DTO
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setPaymentId(id);

        //pass this internalDTO obj to further to process.
        PaymentResponseDTO paymentResponseDTO = paymentService.getPaymentDetailsById(paymentRequestDTO);

        //return the response DTO
        return ResponseEntity.ok(paymentResponseDTO);
    }



    }
