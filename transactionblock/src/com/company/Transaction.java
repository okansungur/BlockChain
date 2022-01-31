package com.company;

public class Transaction {
    public String sender;
    public String recipient;
    public float value;
    public Transaction(String sender, String receiver, float value) {
        this.sender = sender;
        this.recipient = receiver;
        this.value = value;
    }
}