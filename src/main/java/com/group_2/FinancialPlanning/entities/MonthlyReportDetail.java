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
public class MonthlyReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer monthlyReportDetailsId;
    private String expense;
    private String costType;
    private BigDecimal unitPrice;
    private Integer amount;
    private BigDecimal total;
    private String projectName;
    private String note;
    private Integer reportId;
    private BigDecimal totalExpense;
    private Integer totalUser;
}