package com.group_2.FinancialPlanning.repositories;

import com.group_2.FinancialPlanning.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsRepository extends JpaRepository<Term, Integer> {

    // Tìm term theo tên
    Term findByTermName(String termName);

    // Tìm term theo status (nếu cần)
    List<Term> findByStatus(Term.Status status);

    // Tìm term theo duration (nếu cần)
    List<Term> findByDuration(Term.Duration duration);
}