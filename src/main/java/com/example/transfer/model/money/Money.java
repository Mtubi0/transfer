package com.example.transfer.model.money;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {

    private Currency currency;

    private BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
}
