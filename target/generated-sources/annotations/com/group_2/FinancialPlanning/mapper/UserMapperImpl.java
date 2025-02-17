package com.group_2.FinancialPlanning.mapper;

import com.group_2.FinancialPlanning.dto.request.UserCreationRequest;
import com.group_2.FinancialPlanning.dto.response.UserResponse;
import com.group_2.FinancialPlanning.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-17T21:37:27+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest user) {
        if ( user == null ) {
            return null;
        }

        User.UserBuilder user1 = User.builder();

        user1.full_name( user.getFull_name() );
        user1.email( user.getEmail() );
        user1.password( user.getPassword() );

        return user1.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        return userResponse;
    }
}
