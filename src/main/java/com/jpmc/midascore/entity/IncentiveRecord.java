package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class IncentiveRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @Column(nullable = false)
    private BigDecimal incentiveAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRecord user;

    public IncentiveRecord() {}

    public IncentiveRecord(UserRecord user, BigDecimal transactionAmount, BigDecimal incentiveAmount) {
        this.user = user;
        this.transactionAmount = transactionAmount;
        this.incentiveAmount = incentiveAmount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getIncentiveAmount() {
        return incentiveAmount;
    }

    public void setIncentiveAmount(BigDecimal incentiveAmount) {
        this.incentiveAmount = incentiveAmount;
    }

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }
}
