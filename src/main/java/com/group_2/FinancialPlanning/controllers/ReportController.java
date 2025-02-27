package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.entities.MonthlyReport;
import com.group_2.FinancialPlanning.entities.User;
import com.group_2.FinancialPlanning.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    // Constructor injection
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/import-report")
    public ResponseEntity<String> importReport(@RequestParam("file") MultipartFile file,
                                               @RequestParam("term") String term,
                                               @RequestParam("month") String month) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded");
            }
            if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                return ResponseEntity.badRequest().body("Only .xlsx files are allowed");
            }

            // Gọi service để xử lý file Excel
            reportService.processExcelFile(file, term, month);
            return ResponseEntity.ok("File imported successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Invalid file format: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error importing file: " + e.getMessage());
        }
    }
    @GetMapping("/reports")
    public List<MonthlyReport> getAllReports() {
        return reportService.getAllReports();
    }

    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<String> deleteMonthlyReport(@PathVariable Integer reportId) {
        try {
            reportService.deleteMonthlyReport(reportId);
            return ResponseEntity.ok("Report deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting report: " + e.getMessage());
        }
    }
}