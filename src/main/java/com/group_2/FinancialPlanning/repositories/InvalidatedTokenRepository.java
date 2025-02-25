package com.group_2.FinancialPlanning.repositories;

import com.group_2.FinancialPlanning.entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
