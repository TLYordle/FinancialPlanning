package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.entities.FinancialPlanDetail;
import com.group_2.FinancialPlanning.services.FinancialPlanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financial-plans")
public class FinancialPlanDetailController {

    @Autowired
    private FinancialPlanDetailService financialplandetailservice;

    @PostMapping("/import")
    public ResponseEntity<FinancialPlanDetail> importPlan(@RequestBody FinancialPlanDetail detail) {
        FinancialPlanDetail savedDetail = financialplandetailservice.importPlan(detail);
        return new ResponseEntity<>(savedDetail, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
        financialplandetailservice.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view")
    public ResponseEntity<List<FinancialPlanDetail>> viewPlans() {
        List<FinancialPlanDetail> plans = financialplandetailservice.viewPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialPlanDetail> updatePlan(
            @PathVariable Integer id,
            @RequestBody FinancialPlanDetail updatedDetail) {
        FinancialPlanDetail plan = financialplandetailservice.updatePlan(id, updatedDetail);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
}
