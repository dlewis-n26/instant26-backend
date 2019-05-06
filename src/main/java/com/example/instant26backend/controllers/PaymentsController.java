package com.example.instant26backend.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
            @RequestParam(name = "r") String referenceText,
            @RequestParam(name = "e") Integer expectedPayments) {
        String paymentId = RandomStringUtils.randomAlphabetic(6);

        Payment payment = new Payment(paymentId, referenceText, expectedPayments);
        Payments.add(payment);

        return paymentId;
    }

    @PostMapping("/{paymentId}")
    @ResponseBody
    public ResponseEntity<?> createTransaction(@PathVariable("paymentId") String paymentId,
                                               @RequestBody @NotNull @Valid Transaction transaction) {
        return Payments.get(paymentId)
                .map(p -> p.transactions.add(transaction))
                .map(b -> ResponseEntity.ok().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
