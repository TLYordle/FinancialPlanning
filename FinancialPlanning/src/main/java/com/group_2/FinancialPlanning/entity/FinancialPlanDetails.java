package com.group_2.FinancialPlanning.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "financial_plan_details")
public class FinancialPlanDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detailId;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private FinancialPlans financialPlans;

    @Column(name = "expense_name", nullable = false)
    private String expenseName;

    @Column(name = "cost_type", nullable = false)
    private String costType;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "total", nullable = false, insertable = false, updatable = false)
    private Double total;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "regulations")
    private String regulations;

}
