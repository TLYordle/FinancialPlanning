package com.group_2.FinancialPlanning.services;

import com.group_2.FinancialPlanning.entities.MonthlyReportDetails;
import com.group_2.FinancialPlanning.repositories.MonthlyReportDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MonthlyReportDetailsService {

    @Autowired
    private MonthlyReportDetailsRepository monthlyReportDetailsRepository;

    // Lấy tất cả các bản ghi trong monthly_report_details
    public List<MonthlyReportDetails> getAllReportDetails() {
        return monthlyReportDetailsRepository.findAll();
    }

    // Lấy danh sách chi tiết báo cáo theo reportId
    public List<MonthlyReportDetails> getReportDetailsByReportId(Integer reportId) {
        return monthlyReportDetailsRepository.findByReportId(reportId);
    }

    // Lưu danh sách monthly report details với tính toán total nếu cần
    public List<MonthlyReportDetails> saveReportDetails(List<MonthlyReportDetails> details) {
        details.forEach(detail -> {
            if (detail.getTotal() == null && detail.getUnitPrice() != null && detail.getAmount() != null) {
                detail.setTotal(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getAmount())));
            } else if (detail.getTotal() == null) {
                detail.setTotal(BigDecimal.ZERO); // Gán mặc định nếu thiếu dữ liệu
            }
        });
        return monthlyReportDetailsRepository.saveAll(details);
    }

    public boolean deleteReportDetail(Integer detailId) {
        Optional<MonthlyReportDetails> detail = monthlyReportDetailsRepository.findById(detailId);
        if (detail.isPresent()) {
            monthlyReportDetailsRepository.deleteById(detailId);
            return true;
        }
        return false;
    }



    // Cập nhật danh sách monthly report details theo monthlyReportDetailsId
    public List<MonthlyReportDetails> updateReportDetailsById(Integer reportId, List<MonthlyReportDetails> details) {
        for (MonthlyReportDetails detail : details) {
            // Kiểm tra nếu monthlyReportDetailsId tồn tại
            if (detail.getMonthlyReportDetailsId() != null) {
                Optional<MonthlyReportDetails> existingDetail = monthlyReportDetailsRepository.findById(detail.getMonthlyReportDetailsId());
                if (existingDetail.isPresent()) {
                    MonthlyReportDetails existing = existingDetail.get();
                    // Cập nhật các trường nếu có giá trị mới
                    if (detail.getExpense() != null) existing.setExpense(detail.getExpense());
                    if (detail.getCostType() != null) existing.setCostType(detail.getCostType());
                    if (detail.getUnitPrice() != null) existing.setUnitPrice(detail.getUnitPrice());
                    if (detail.getAmount() != null) existing.setAmount(detail.getAmount());
                    if (detail.getProjectName() != null) existing.setProjectName(detail.getProjectName());
                    if (detail.getNote() != null) existing.setNote(detail.getNote());

                    // Tính lại total nếu unitPrice hoặc amount được cập nhật
                    if (existing.getUnitPrice() != null && existing.getAmount() != null) {
                        existing.setTotal(existing.getUnitPrice().multiply(BigDecimal.valueOf(existing.getAmount())));
                    } else if (existing.getTotal() == null) {
                        existing.setTotal(BigDecimal.ZERO);
                    }

                    // Đảm bảo reportId vẫn đúng
                    existing.setReportId(reportId);
                    monthlyReportDetailsRepository.save(existing);
                } else {
                    // Nếu không tìm thấy bản ghi với monthlyReportDetailsId, tạo mới (nếu cần)
                    detail.setReportId(reportId);
                    if (detail.getTotal() == null && detail.getUnitPrice() != null && detail.getAmount() != null) {
                        detail.setTotal(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getAmount())));
                    } else if (detail.getTotal() == null) {
                        detail.setTotal(BigDecimal.ZERO);
                    }
                    monthlyReportDetailsRepository.save(detail);
                }
            } else {
                // Nếu không có monthlyReportDetailsId, coi như bản ghi mới
                detail.setReportId(reportId);
                if (detail.getTotal() == null && detail.getUnitPrice() != null && detail.getAmount() != null) {
                    detail.setTotal(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getAmount())));
                } else if (detail.getTotal() == null) {
                    detail.setTotal(BigDecimal.ZERO);
                }
                monthlyReportDetailsRepository.save(detail);
            }
        }
        // Trả về danh sách chi tiết đã cập nhật
        return monthlyReportDetailsRepository.findByReportId(reportId);
    }
}