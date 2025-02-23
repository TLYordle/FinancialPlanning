package com.group_2.FinancialPlanning.service;


import com.group_2.FinancialPlanning.dto.request.PermissionRequest;
import com.group_2.FinancialPlanning.dto.response.PermissionResponse;
import com.group_2.FinancialPlanning.entity.Permission;
import com.group_2.FinancialPlanning.mapper.PermissionMapper;
import com.group_2.FinancialPlanning.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasPermission('READ_DATA')")
    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
