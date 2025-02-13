package com.group_2.FinancialPlanning.service;

import com.group_2.FinancialPlanning.constant.PredefinedRole;
import com.group_2.FinancialPlanning.dto.request.UserCreationRequest;
import com.group_2.FinancialPlanning.dto.response.UserResponse;
import com.group_2.FinancialPlanning.entity.Role;
import com.group_2.FinancialPlanning.entity.User;
import com.group_2.FinancialPlanning.exception.AppException;
import com.group_2.FinancialPlanning.exception.ErrorCode;
import com.group_2.FinancialPlanning.mapper.UserMapper;
import com.group_2.FinancialPlanning.repository.RoleRepository;
import com.group_2.FinancialPlanning.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.STAFF_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
