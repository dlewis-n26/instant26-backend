package com.example.instant26backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping(path = "/payments")
@RestController
public class PaymentsController {

    @GetMapping
    public Object listPayments() {
        return Payments.list();
    }

    @GetMapping("/poll/{paymentId}")
    @ResponseBody
    public ResponseEntity<?> poll(@PathVariable("paymentId") String paymentId) {
        return Payments.get(paymentId)
                .map(p -> ResponseEntity.ok(statusPayload(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createPayment(@RequestBody @NotNull @Valid PaymentDto dto) {
        Payments.add(new Payment(dto));

        return ResponseEntity.accepted().build();
    }

    @PostMapping(path = "/{paymentId}/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createTransaction(@PathVariable("paymentId") String paymentId,
                                               @RequestBody @NotNull @Valid Transaction transaction) {
        return Payments.get(paymentId)
                .map(p -> p.transactions.add(transaction))
                .map(b -> ResponseEntity.ok().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private String statusPayload(Payment payment) {
        return payment.transactions.stream()
                .filter(t -> !t.shown)
                .findFirst()
                .map(t -> {
                    t.shown = true;
                    return "Received " + payment.dto.getAmount() + " EUR from " + t.sender;
                })
                .orElse("");
    }
}
