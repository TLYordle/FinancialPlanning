package com.group_2.FinancialPlanning.services;

import com.group_2.FinancialPlanning.entities.FinancialPlan;
import com.group_2.FinancialPlanning.repositories.FinancialPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialPlanService {

    @Autowired
    private FinancialPlanRepository financialPlanRepository;

    // Import a new financial plan
    public FinancialPlan importPlan(FinancialPlan financialPlan) {
        return financialPlanRepository.save(financialPlan);
    }

    // Delete a financial plan by ID
    public void deletePlan(Integer planId) {
        FinancialPlan plan = financialPlanRepository.findById(planId).orElseThrow(() -> new RuntimeException("Plan not found"));
        plan.setIsdeleted(true);
        financialPlanRepository.save(plan);
    }

    // View (export) a financial plan by ID
    public Optional<FinancialPlan> viewPlan(Integer planId) {
        return financialPlanRepository.findById(planId);
    }

    // Update (re-upload) a financial plan
    public FinancialPlan updatePlan(Integer planId, FinancialPlan updatedPlan) {
        updatedPlan.setPlanId(planId);
        return financialPlanRepository.save(updatedPlan);
    }

    // Get all plans
    public List<FinancialPlan> getAllPlans() {
        return financialPlanRepository.findAll();
    }
}
