package com.bilin.handler;

import com.bilin.constant.MessageConstant;
import com.bilin.exception.BaseException;
import com.bilin.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("Exception message：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result handleSQLException(SQLIntegrityConstraintViolationException ex){
        log.error("Exception: {}", ex.getMessage());
        String message = ex.getMessage();
        if (message.contains("Duplicate")){
            return Result.error(message.split(" ")[2] + " " + MessageConstant.ALREADY_EXISTS);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
