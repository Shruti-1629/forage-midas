package com.jpmc.midascore.foundation;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    // ✅ Default constructor needed for JPA
    public Balance() {
    }

    // Existing constructor
    public Balance(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BEGIN_BALANCE_OUTPUT\n" +
                amount + "\n" +
                "END_BALANCE_OUTPUT";
    }
}
