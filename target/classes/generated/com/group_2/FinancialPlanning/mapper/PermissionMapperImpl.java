package com.group_2.FinancialPlanning.mapper;

import com.group_2.FinancialPlanning.dto.request.PermissionRequest;
import com.group_2.FinancialPlanning.dto.response.PermissionResponse;
import com.group_2.FinancialPlanning.entity.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-23T23:52:11+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(PermissionRequest request) {
        if ( request == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.name( request.getName() );
        permission.description( request.getDescription() );

        return permission.build();
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.name( permission.getName() );
        permissionResponse.description( permission.getDescription() );

        return permissionResponse.build();
    }
}
