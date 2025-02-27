package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.dto.request.AuthenticationRequest;
import com.group_2.FinancialPlanning.dto.request.ForgotPasswordRequest;
import com.group_2.FinancialPlanning.dto.request.IntrospectRequest;
import com.group_2.FinancialPlanning.dto.request.LogoutRequest;
import com.group_2.FinancialPlanning.dto.response.ApiResponse;
import com.group_2.FinancialPlanning.dto.response.AuthenticationResponse;
import com.group_2.FinancialPlanning.dto.response.IntrospectResponse;
import com.group_2.FinancialPlanning.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/forgot-password")
    ApiResponse<String> forgotPassword(@RequestBody ForgotPasswordRequest request){
        authenticationService.forgotPassword(request);
        return ApiResponse.<String>builder()
                .message("Success to send new password to " + request.getEmail())
                .build();
    }
}
