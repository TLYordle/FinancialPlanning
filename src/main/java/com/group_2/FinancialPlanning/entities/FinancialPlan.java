package com.group_2.FinancialPlanning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "financial_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "id_unique", nullable = false)
    private Integer idUnique;

    @Column(name = "plan_name", length = 255, nullable = false)
    private String planName;

    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term termId;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NEW;

    @Column(name = "version")
    private Integer version = 1;

    @Column(name = "is_deleted")
    private boolean isdeleted = false;

    public enum Status {
        NEW,
        WAITING_FOR_APPROVAL,
        APPROVED,
        CLOSED
    }
}
