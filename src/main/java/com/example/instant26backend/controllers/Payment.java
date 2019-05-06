package com.example.instant26backend.controllers;

import java.util.ArrayList;
import java.util.List;

public class Payment {

    public PaymentDto dto;

    public List<Transaction> transactions;

    public Payment(PaymentDto dto) {
        this.dto = dto;
        this.transactions = new ArrayList<>();
    }
}
