package com.group_2.FinancialPlanning.repository;

import com.group_2.FinancialPlanning.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Integer> {

    // Tìm term theo tên
    Terms findByTermName(String termName);

    // Tìm term theo status (nếu cần)
    List<Terms> findByStatus(Terms.Status status);

    // Tìm term theo duration (nếu cần)
    List<Terms> findByDuration(Terms.Duration duration);
}