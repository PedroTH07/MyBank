package com.project.MyBank.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;
    
    @ManyToOne
    @JoinColumn(name = "payer")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "payee")
    private User payee;

    @Column(nullable = false)
    private Double amount;

    // TIMESTAMP WITHOUT TIME ZONE no postgres
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp = LocalDateTime.now();
}
