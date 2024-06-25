package uz.resultme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.resultme.exception.IllegalPhotoTypeException;
import uz.resultme.exception.LanguageNotSupportException;
import uz.resultme.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(IllegalPhotoTypeException.class)
    public ResponseEntity<ApiResponse<?>> handlePhotoTypeException(IllegalPhotoTypeException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Illegal photo" + ex.getMessage(), null));
    }

    @ExceptionHandler(LanguageNotSupportException.class)
    public ResponseEntity<ApiResponse<?>> handleLanguageException(LanguageNotSupportException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ex.getMessage(), null));
    }

}
