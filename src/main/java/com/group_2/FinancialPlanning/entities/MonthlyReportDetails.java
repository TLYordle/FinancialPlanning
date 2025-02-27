package com.group_2.FinancialPlanning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "monthly_report_details")
public class MonthlyReportDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer monthlyReportDetailsId;
    private String expense;
    private String costType;
    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private Integer amount;
    @Column(insertable = false, updatable = false)
    private BigDecimal total;
    private String projectName;
    private String note;
    private Integer reportId; // Đây là trường bình thường, không phải quan hệ
    private BigDecimal totalExpense;
    private Integer totalUser;
}