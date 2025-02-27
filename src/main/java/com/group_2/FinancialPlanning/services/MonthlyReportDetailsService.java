package com.group_2.FinancialPlanning.services;

import com.group_2.FinancialPlanning.entities.MonthlyReportDetails;
import com.group_2.FinancialPlanning.repositories.MonthlyReportDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
