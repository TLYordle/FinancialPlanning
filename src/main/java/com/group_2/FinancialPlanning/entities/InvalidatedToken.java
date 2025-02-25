package com.group_2.FinancialPlanning.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "invalidated_token")
public class InvalidatedToken {
    @Id
    private String id;
    @Column(name = "")
    private Date expiryTime;
}
