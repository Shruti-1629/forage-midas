package com.jpmc.midascore.entity;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.converter.BalanceConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = BalanceConverter.class)
    @Column(nullable = false)
    private Balance balance;

    // Constructors
    public UserRecord() {}

    public UserRecord(String name, Balance balance) {
        this.name = name;
        this.balance = balance;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}
