package com.group_2.FinancialPlanning.repository;

import com.group_2.FinancialPlanning.entity.MonthlyReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReportDetailsRepository extends JpaRepository<MonthlyReportDetail, Integer> {

    // Tìm tất cả chi tiết báo cáo theo report_id
    List<MonthlyReportDetail> findByReportId(Integer reportId);

    // Tìm chi tiết báo cáo theo expense (nếu cần)
    List<MonthlyReportDetail> findByExpense(String expense);

    // Tìm chi tiết báo cáo theo cost_type (nếu cần)
    List<MonthlyReportDetail> findByCostType(String costType);
}