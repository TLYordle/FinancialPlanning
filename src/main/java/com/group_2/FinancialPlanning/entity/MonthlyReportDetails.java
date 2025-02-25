package com.group_2.FinancialPlanning.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "monthly_report_details")
public class MonthlyReportDetails {
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

    public Integer getMonthlyReportDetailsId() {
        return monthlyReportDetailsId;
    }

    public void setMonthlyReportDetailsId(Integer monthlyReportDetailsId) {
        this.monthlyReportDetailsId = monthlyReportDetailsId;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Integer getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Integer totalUser) {
        this.totalUser = totalUser;
    }
}