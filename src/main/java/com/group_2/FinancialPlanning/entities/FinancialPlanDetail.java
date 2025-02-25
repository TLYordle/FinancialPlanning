package com.group_2.FinancialPlanning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "financial_plan_details")
public class FinancialPlanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detailId;

    @Column(nullable = false, length = 255)
    private String expense;

    @Column(name = "cost_type", nullable = false, length = 255)
    private String costType;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer amount;

    @Column(name = "total", nullable = false, updatable = false)
    private BigDecimal total;

    @Column(name = "project_name", length = 255)
    private String projectName;

    @Column(length = 6000)
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_expense", nullable = false)
    private StatusExpense statusExpense = StatusExpense.NEW;

    @ManyToOne
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id")
    private FinancialPlan financialPlan;

    public enum StatusExpense {
        NEW,
        WAITING_FOR_APPROVAL,
        APPROVED
    }
}
