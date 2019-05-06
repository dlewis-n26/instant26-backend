package com.example.instant26backend.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(path = "/payments")
@RestController
public class PaymentsController {

    private Map<String, Payment> payments;

    @GetMapping
    public Object listPayments() {
        return Payments.list();
    }

    @PostMapping
    @ResponseBody
    public String createPayment(
            @RequestParam(name = "referenceText") String referenceText,
            @RequestParam(name = "expectedPayments") Integer expectedPayments) {
        String paymentId = RandomStringUtils.randomAlphabetic(6);

        Payment payment = new Payment(paymentId, referenceText, expectedPayments);
        Payments.add(payment);

        return paymentId;
    }
}
