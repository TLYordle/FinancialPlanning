package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.entities.FinancialPlan;
import com.group_2.FinancialPlanning.services.FinancialPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/financialplans")
@CrossOrigin(origins = "*")
public class FinancialPlanController {

    @Autowired
    private FinancialPlanService financialPlanService;

    // Import a new financial plan
    @PostMapping("/import")
    public ResponseEntity<FinancialPlan> importPlan(@RequestBody FinancialPlan financialPlan) {
        FinancialPlan savedPlan = financialPlanService.importPlan(financialPlan);
        return ResponseEntity.ok(savedPlan);
    }

    // Delete a financial plan
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer planId) {
        financialPlanService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }

    // View (export) a financial plan
    @GetMapping("/{planId}")
    public ResponseEntity<FinancialPlan> viewPlan(@PathVariable Integer planId) {
        Optional<FinancialPlan> plan = financialPlanService.viewPlan(planId);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update (re-upload) a financial plan
    @PutMapping("/{planId}")
    public ResponseEntity<FinancialPlan> updatePlan(@PathVariable Integer planId, @RequestBody FinancialPlan updatedPlan) {
        FinancialPlan plan = financialPlanService.updatePlan(planId, updatedPlan);
        return ResponseEntity.ok(plan);
    }

    // Get all plans
    @GetMapping("/list")
    public List<FinancialPlan> getAllplans() {
        return financialPlanService.getAllPlans();
    }
}
