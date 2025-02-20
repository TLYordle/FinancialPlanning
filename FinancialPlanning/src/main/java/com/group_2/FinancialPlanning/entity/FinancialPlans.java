package com.group_2.FinancialPlanning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "financial_plans")
public class FinancialPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

//    @ManyToOne
//    @JoinColumn(name = "term_id")
//    private Term term;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PlanStatus status;

    @Column(name = "version", nullable = false)
    private Integer version;
}
