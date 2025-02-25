package com.group_2.FinancialPlanning.repository;

import com.group_2.FinancialPlanning.entity.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Integer> {

    // Tìm báo cáo theo tên và tháng (nếu cần)
    MonthlyReport findByReportNameAndMonthName(String reportName, String monthName);

    // Tìm báo cáo theo term_id (nếu cần)
    MonthlyReport findByTermId(Integer termId);

    // Tìm báo cáo theo user_id (nếu cần)
    MonthlyReport findByUserId(Integer userId);
}