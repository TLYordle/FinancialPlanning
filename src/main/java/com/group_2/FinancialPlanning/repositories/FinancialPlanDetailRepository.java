package com.group_2.FinancialPlanning.repositories;

import com.group_2.FinancialPlanning.entities.FinancialPlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialPlanDetailRepository extends JpaRepository<FinancialPlanDetail, Integer> {
    // Additional query methods (if needed) can be defined here
}
