package com.group_2.FinancialPlanning.mapper;

import com.group_2.FinancialPlanning.dto.request.UserCreationRequest;
import com.group_2.FinancialPlanning.dto.request.UserUpdatingRequest;
import com.group_2.FinancialPlanning.dto.response.UserResponse;
import com.group_2.FinancialPlanning.entity.Role;
import com.group_2.FinancialPlanning.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-23T23:08:24+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.full_name( request.getFull_name() );
        user.password( request.getPassword() );
        user.email( request.getEmail() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.full_name( user.getFull_name() );
        userResponse.email( user.getEmail() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userResponse.roles( new LinkedHashSet<Role>( set ) );
        }
        userResponse.isActive( user.getIsActive() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );

        return userResponse.build();
    }

    @Override
    public void toUpdateUser(UserUpdatingRequest request, User user) {
        if ( request == null ) {
            return;
        }

        user.setFull_name( request.getFull_name() );
    }
}
