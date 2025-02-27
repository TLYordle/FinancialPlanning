package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.entities.MonthlyReportDetails;
import com.group_2.FinancialPlanning.services.MonthlyReportDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report_details")
public class MonthlyReportDetailsController {

    @Autowired
    private MonthlyReportDetailsService monthlyReportDetailsService;

    // API: Lấy tất cả chi tiết báo cáo
    @GetMapping
    public ResponseEntity<List<MonthlyReportDetails>> getAllReportDetails() {
        List<MonthlyReportDetails> details = monthlyReportDetailsService.getAllReportDetails();
        return ResponseEntity.ok(details);
    }

    // API: Lấy chi tiết báo cáo theo reportId
    @GetMapping("/{reportId}")
    public ResponseEntity<List<MonthlyReportDetails>> getReportDetailsByReportId(@PathVariable Integer reportId) {
        List<MonthlyReportDetails> details = monthlyReportDetailsService.getReportDetailsByReportId(reportId);
        return ResponseEntity.ok(details);
    }
}
