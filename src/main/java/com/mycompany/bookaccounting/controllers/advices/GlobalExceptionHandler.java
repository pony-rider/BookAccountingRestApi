/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.bookaccounting.controllers.advices;

import com.mycompany.bookaccounting.controllers.ErrorDetails;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails jsonParseErrortHandler(HttpMessageNotReadableException ex) {        
        return new ErrorDetails("input parsing error");        
    }
    
    
//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ErrorDetails internalErrortHandler(Exception ex) {        
//        return new ErrorDetails(ex.getMessage());        
//    }
    
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails constraintViolationErrortHandler(ConstraintViolationException ex) {        
        return new ErrorDetails("can't delete entity which has dependent entities");        
    }
    
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails invalidInputParamErrortHandler(MethodArgumentTypeMismatchException ex) {        
        return new ErrorDetails("invalid input params");        
    }
    
    
}
