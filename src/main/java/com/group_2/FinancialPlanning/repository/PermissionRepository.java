package com.group_2.FinancialPlanning.repository;

import com.group_2.FinancialPlanning.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
