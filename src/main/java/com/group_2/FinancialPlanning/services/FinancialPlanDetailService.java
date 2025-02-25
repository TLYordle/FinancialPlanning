package com.group_2.FinancialPlanning.services;

import com.group_2.FinancialPlanning.repositories.FinancialPlanDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group_2.FinancialPlanning.entities.FinancialPlanDetail;

import java.util.List;

@Service
public class FinancialPlanDetailService {

    @Autowired
    private FinancialPlanDetailRepository financialplandetailrepository;

    public FinancialPlanDetail importPlan(FinancialPlanDetail detail) {
        return financialplandetailrepository.save(detail);
    }

    public void deletePlan(Integer detailId) {
        financialplandetailrepository.deleteById(detailId);
    }

    public List<FinancialPlanDetail> viewPlans() {
        return financialplandetailrepository.findAll();
    }

    public FinancialPlanDetail updatePlan(Integer detailId, FinancialPlanDetail updatedDetail) {
        FinancialPlanDetail existingDetail = financialplandetailrepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        existingDetail.setExpense(updatedDetail.getExpense());
        existingDetail.setCostType(updatedDetail.getCostType());
        existingDetail.setUnitPrice(updatedDetail.getUnitPrice());
        existingDetail.setAmount(updatedDetail.getAmount());
        existingDetail.setProjectName(updatedDetail.getProjectName());
        existingDetail.setNote(updatedDetail.getNote());
        existingDetail.setStatusExpense(updatedDetail.getStatusExpense());
        // Update total if necessary (or handle it in DB)
        return financialplandetailrepository.save(existingDetail);
    }
}
