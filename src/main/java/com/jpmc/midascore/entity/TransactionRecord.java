package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class TransactionRecord {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne @JoinColumn(name="sender_id", nullable=false)
    private UserRecord sender;

    @ManyToOne @JoinColumn(name="recipient_id", nullable=false)
    private UserRecord recipient;

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    // Constructors, getters, setters
}
