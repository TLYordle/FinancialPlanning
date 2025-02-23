package com.group_2.FinancialPlanning.dto.request;

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
    private String password;
    private String email;
}
