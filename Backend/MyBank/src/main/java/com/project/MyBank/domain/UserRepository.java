package com.project.MyBank.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email = :payerEmail OR email = :payeeEmail", nativeQuery = true)
    List<User> findUsersForPayment(@Param("payerEmail") String payerEmail, @Param("payeeEmail") String payeeEmail);

    @EntityGraph(attributePaths = {"payerTransactions", "payeeTransactions"})
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailWithTransactions(@Param("email") String email);
}
