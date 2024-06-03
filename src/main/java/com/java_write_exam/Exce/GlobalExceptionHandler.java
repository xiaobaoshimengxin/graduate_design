package com.java_write_exam.Exce;

import com.java_write_exam.util.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    public Result<String> ex(Exception exception){
        exception.printStackTrace();
        String e = "出错了 T_T";
        return Result.error(e);
    }


}
