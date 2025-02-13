package com.group_2.FinancialPlanning.dto.response;

import com.group_2.FinancialPlanning.entity.Role;

import java.time.LocalDateTime;
import java.util.Set;

public class UserResponse {
    private String id;
    private String full_name;
    private String email;
    private Set<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
