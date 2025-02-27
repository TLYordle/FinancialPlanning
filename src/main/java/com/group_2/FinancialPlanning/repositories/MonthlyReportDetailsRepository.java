package com.group_2.FinancialPlanning.repositories;

import com.group_2.FinancialPlanning.entities.MonthlyReportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReportDetailsRepository extends JpaRepository<MonthlyReportDetails, Integer> {

    // Tìm tất cả chi tiết báo cáo theo report_id
    List<MonthlyReportDetails> findByReportId(Integer reportId);

    // Tìm chi tiết báo cáo theo expense (nếu cần)
    List<MonthlyReportDetails> findByExpense(String expense);

    // Tìm chi tiết báo cáo theo cost_type (nếu cần)
    List<MonthlyReportDetails> findByCostType(String costType);

}