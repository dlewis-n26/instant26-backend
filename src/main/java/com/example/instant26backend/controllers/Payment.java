package com.example.instant26backend.controllers;

import java.util.ArrayList;
import java.util.List;

public class Payment {

    public String id;

    public String referenceText;

    public int expectedPayments;

    public List<Transaction> transactions;

    public Payment(String id, String referenceText, int expectedPayments) {
        this.id = id;
        this.referenceText = referenceText;
        this.expectedPayments = expectedPayments;
        this.transactions = new ArrayList<>();
    }
}
