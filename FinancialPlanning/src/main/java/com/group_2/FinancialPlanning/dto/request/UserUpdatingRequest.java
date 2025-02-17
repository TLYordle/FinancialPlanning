package com.group_2.FinancialPlanning.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatingRequest {
    private String password;
    private String full_name;
    List<String> roles;
}
