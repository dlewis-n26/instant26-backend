package com.example.instant26backend.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Payments {

    private static Map<String, Payment> payments = new HashMap<>();

    public static void add(Payment payment) {
        payments.put(payment.dto.getId(), payment);
    }

    public static Optional<Payment> get(String paymentId) {
        return Optional.of(payments.get(paymentId));
    }

    public static List<Payment> list() {
        return new ArrayList<>(payments.values());
    }

}
