package com.group_2.FinancialPlanning.repositories;

import com.group_2.FinancialPlanning.entities.MonthlyReport;
import com.group_2.FinancialPlanning.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Integer> {

    MonthlyReport findByReportNameAndMonthName(String reportName, String monthName);

    MonthlyReport findByTerm(Term term);

    // Tìm báo cáo theo tháng hoặc kỳ hạn (tùy chọn)
    List<MonthlyReport> findByMonthName(String monthName);
    void deleteByReportId(Integer reportId);
}
