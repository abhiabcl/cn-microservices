package com.ft.accounts.repository;

import com.ft.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional  //handling the rollback in case of any failure
    @Modifying  // going to modify the data , it should be considered in transaction
    void deleteByCustomerId(Long customerId);
}
