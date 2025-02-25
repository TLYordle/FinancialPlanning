package com.group_2.FinancialPlanning.controller;

import com.group_2.FinancialPlanning.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

            // Kiểm tra định dạng file (nếu cần)
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
}