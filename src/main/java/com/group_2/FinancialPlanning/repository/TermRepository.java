package com.group_2.FinancialPlanning.repository;

import com.group_2.FinancialPlanning.entity.Term;
import com.group_2.FinancialPlanning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Integer> {
    // Lay term do 1 user tao ra
    //List<Term> findByCreatedBy(User createdBy);
}