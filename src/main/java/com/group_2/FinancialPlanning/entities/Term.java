package com.group_2.FinancialPlanning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
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
        MONTHLY, QUARTERLY, HALFYEAR
    }

    // Enum cho status
    public enum Status {
            NEW, INPROGESS, CLOSED
    }

    // Constructors
    public Term() {
        this.startDate = LocalDateTime.now(); // Mặc định current timestamp
    }
}