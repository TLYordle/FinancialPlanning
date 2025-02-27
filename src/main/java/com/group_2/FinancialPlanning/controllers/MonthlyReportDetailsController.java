package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.entities.MonthlyReportDetails;
import com.group_2.FinancialPlanning.services.MonthlyReportDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    // API: Lưu danh sách monthly report details (từ file Excel)
    @PostMapping("/import")
    public ResponseEntity<List<MonthlyReportDetails>> saveReportDetails(@RequestBody List<MonthlyReportDetails> details) {
        List<MonthlyReportDetails> savedDetails = monthlyReportDetailsService.saveReportDetails(details);
        return ResponseEntity.ok(savedDetails);
    }

    @PutMapping("/update/{reportId}")
    public ResponseEntity<?> updateReportDetailsById(@PathVariable Integer reportId, @RequestBody List<MonthlyReportDetails> details) {
        try {
            // Kiểm tra và cập nhật từng chi tiết dựa trên monthlyReportDetailsId
            List<MonthlyReportDetails> updatedDetails = monthlyReportDetailsService.updateReportDetailsById(reportId, details);
            return ResponseEntity.ok(updatedDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Lỗi khi cập nhật chi tiết báo cáo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{detailId}")
    public ResponseEntity<?> deleteReportDetail(@PathVariable Integer detailId) {
        boolean isDeleted = monthlyReportDetailsService.deleteReportDetail(detailId);
        if (isDeleted) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Xóa thành công!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Không tìm thấy báo cáo chi tiết!"));
        }
    }
}
