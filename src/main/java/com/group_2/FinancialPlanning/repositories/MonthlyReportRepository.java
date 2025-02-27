package com.group_2.FinancialPlanning.repositories;

import com.group_2.FinancialPlanning.entities.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Integer> {

    // Tìm báo cáo theo tên và tháng (nếu cần)
    MonthlyReport findByReportNameAndMonthName(String reportName, String monthName);

    // Tìm báo cáo theo term_id (nếu cần)
    MonthlyReport findByTermId(Integer termId);

    // Tìm báo cáo theo tháng hoặc kỳ hạn (tùy chọn)
    List<MonthlyReport> findByMonthName(String monthName);
    void deleteByReportId(Integer reportId);
}