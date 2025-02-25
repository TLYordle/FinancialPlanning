package com.group_2.FinancialPlanning.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "terms")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Integer termId;

    @Column(name = "term_name", nullable = false)
    private String termName;

    @Column(name = "duration", nullable = false)
    @Enumerated(EnumType.STRING)
    private Duration duration;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "plan_due_date", nullable = false)
    private LocalDate planDueDate;

    @Column(name = "report_due_date", nullable = false)
    private LocalDate reportDueDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_by")
    private Integer createdBy;

    // Enum cho duration
    public enum Duration {
        MONTHLY, QUARTERLY, YEARLY
    }

    // Enum cho status
    public enum Status {
        NEW, IN_PROGRESS, CLOSED
    }

    // Constructors
    public Term() {
        this.startDate = LocalDateTime.now(); // Mặc định current timestamp
    }

    public Term(String termName, Duration duration, LocalDate endDate, LocalDate planDueDate, LocalDate reportDueDate, Status status, Integer createdBy) {
        this.termName = termName;
        this.duration = duration;
        this.endDate = endDate;
        this.planDueDate = planDueDate;
        this.reportDueDate = reportDueDate;
        this.status = status;
        this.createdBy = createdBy;
        this.startDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getPlanDueDate() {
        return planDueDate;
    }

    public void setPlanDueDate(LocalDate planDueDate) {
        this.planDueDate = planDueDate;
    }

    public LocalDate getReportDueDate() {
        return reportDueDate;
    }

    public void setReportDueDate(LocalDate reportDueDate) {
        this.reportDueDate = reportDueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}