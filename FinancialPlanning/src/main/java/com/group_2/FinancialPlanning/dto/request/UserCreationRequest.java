package com.group_2.FinancialPlanning.dto.request;

import com.group_2.FinancialPlanning.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
    private String full_name;
    private String email;
    private String password;
}
