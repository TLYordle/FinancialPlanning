package com.group_2.FinancialPlanning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "monthly_report")
public class MonthlyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @Column(name = "report_name", nullable = false)
    private String reportName;

    @Column(name = "month_name", nullable = false)
    private String monthName;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false) // Liên kết với cột term_id trong bảng monthly_report
    private Term term;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Liên kết với cột user_id trong bảng monthly_report
    private User user;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "report_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reportDate;

    @Column(name = "version")
    private Integer version;
}