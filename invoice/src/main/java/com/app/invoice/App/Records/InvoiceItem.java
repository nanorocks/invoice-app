package com.app.invoice.App.Records;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class InvoiceItem {
    private String name;
    private String description;
    private int quantity;
    private double price;

    public InvoiceItem(String name, String description, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // Constructors, getters, setters...
}
