package com.group_2.FinancialPlanning.mapper;


import com.group_2.FinancialPlanning.dto.request.RoleRequest;
import com.group_2.FinancialPlanning.dto.response.RoleResponse;
import com.group_2.FinancialPlanning.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
