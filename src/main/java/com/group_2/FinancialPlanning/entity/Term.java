package com.group_2.FinancialPlanning.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "terms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer termId;

    @Column(name = "term_name", nullable = false)
    private String termName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Duration duration;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "plan_due_date", nullable = false)
    private LocalDate planDueDate;

    @Column(name = "report_due_date", nullable = false)
    private LocalDate reportDueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public enum Duration {
        monthly, quarterly, yearly
    }

    public enum Status {
        active, inactive, archived
    }
}
