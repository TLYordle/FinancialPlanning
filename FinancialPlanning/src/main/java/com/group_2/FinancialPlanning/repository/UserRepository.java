package com.group_2.FinancialPlanning.repository;

import com.group_2.FinancialPlanning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String id);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByIsActiveTrue();
}
