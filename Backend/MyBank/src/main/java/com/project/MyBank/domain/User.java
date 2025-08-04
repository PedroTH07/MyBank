package com.project.MyBank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Double money;

    private String imageUrl;

    @OneToMany(mappedBy = "payer", fetch = FetchType.LAZY)
    private Set<Transaction> payerTransactions = new HashSet<>();

    @OneToMany(mappedBy = "payee", fetch = FetchType.LAZY)
    private Set<Transaction> payeeTransactions = new HashSet<>();
}
