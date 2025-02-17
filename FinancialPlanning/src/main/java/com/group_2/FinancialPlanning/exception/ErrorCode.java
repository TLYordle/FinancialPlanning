package com.group_2.FinancialPlanning.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    STORY_NOT_FOUND(404,"Story not found", HttpStatus.NOT_FOUND),
    STORY_EXISTED(409,"Story already existed", HttpStatus.CONFLICT),
    AUTHOR_NOT_FOUND(404,"Author not found", HttpStatus.NOT_FOUND),
    AUTHOR_EXISTED(409,"Author already existed", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(404,"Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(409,"Category already existed", HttpStatus.CONFLICT),
    CHAPTER_NOT_FOUND(404,"Chapter not found", HttpStatus.NOT_FOUND),
    CHAPTER_EXISTED(409,"Chapter already existed", HttpStatus.CONFLICT),
    USER_NOT_FOUND(404,"User not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(409,"Username already existed", HttpStatus.CONFLICT),
    COMMENT_NOT_FOUND(404,"Comment not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401,"Unauthenticated", HttpStatus.UNAUTHORIZED),
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
