package com.group_2.FinancialPlanning.repositories;

import com.group_2.FinancialPlanning.entities.FinancialPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialPlanRepository extends JpaRepository<FinancialPlan, Integer> {

}
