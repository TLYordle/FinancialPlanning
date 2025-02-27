package com.group_2.FinancialPlanning.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_NOT_FOUND(404, "Email not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(404,"User not found", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(409,"Email already existed", HttpStatus.CONFLICT),
    UNAUTHENTICATED(401,"Wrong account or password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_KEY(400, "Invalid key", HttpStatus.BAD_REQUEST),
    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
