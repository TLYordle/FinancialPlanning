package com.group_2.FinancialPlanning.mapper;

import com.group_2.FinancialPlanning.dto.request.UserCreationRequest;
import com.group_2.FinancialPlanning.dto.request.UserUpdatingRequest;
import com.group_2.FinancialPlanning.dto.response.UserResponse;
import com.group_2.FinancialPlanning.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void toUpdateUser(UserUpdatingRequest request, @MappingTarget User user);
}
