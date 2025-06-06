package com.example.SpringSecurity.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //ResourceNotFoundException (404 NOT FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        logger.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
//BadRequestException (400 BAD REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        logger.warn("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //Exception (500 INTERNAL SERVER ERROR, chung cho tất cả lỗi bất ngờ)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpected(Exception ex) {
        logger.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi hệ thống, vui lòng thử lại sau.");
    }

    /*
    1. UnauthorizedException (401 UNAUTHORIZED)
    Khi user chưa đăng nhập hoặc không có token hợp lệ.
    */

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedException ex) {
        logger.warn("Unauthorized: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }


    /*
    2. AccessDeniedException (403 FORBIDDEN)
Khi user đã đăng nhập nhưng không có quyền truy cập tài nguyên.
     */

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
//        logger.warn("Access denied: {}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền truy cập.");
//    }

//3. MethodArgumentNotValidException (400 BAD REQUEST)
//    Khi validation dữ liệu đầu vào (như @Valid) không hợp lệ.
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
    logger.warn("Validation failed: {}", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);


}

    //    4. HttpRequestMethodNotSupportedException (405 METHOD NOT ALLOWED)
//    Khi client gọi API bằng method không được phép (ví dụ POST mà API chỉ cho phép GET).
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        logger.warn("Method not allowed: {}", ex.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Phương thức " + ex.getMethod() + " không được hỗ trợ.");
    }

//    5. DataIntegrityViolationException (409 CONFLICT)
//    Khi dữ liệu vi phạm ràng buộc (ví dụ trùng key, khóa ngoại...).

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//        logger.error("Data integrity violation", ex);
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("Dữ liệu bị trùng hoặc vi phạm ràng buộc.");
//    }

}
