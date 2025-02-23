package com.group_2.FinancialPlanning.mapper;


import com.group_2.FinancialPlanning.dto.request.PermissionRequest;
import com.group_2.FinancialPlanning.dto.response.PermissionResponse;
import com.group_2.FinancialPlanning.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
