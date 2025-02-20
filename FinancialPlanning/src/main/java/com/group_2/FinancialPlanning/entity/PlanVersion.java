package com.group_2.FinancialPlanning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "plan_versions")
public class PlanVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer versionId;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private FinancialPlans financialPlans;

    @Column(name = "version", nullable = false)
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "file_path", nullable = false)
    private String filePath;

}
