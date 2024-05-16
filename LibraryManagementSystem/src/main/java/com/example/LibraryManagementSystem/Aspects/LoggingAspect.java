package com.example.LibraryManagementSystem.Aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.LibraryManagementSystem.Services.*.*(..))")
    private void serviceMethods() {}

    @Before("serviceMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} called.", methodName);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception in method {}: {}", methodName, exception.getMessage());
    }

    @Around("execution(* com.example.LibraryManagementSystem.Controller.BookController.createBook(..)) || " +
            "execution(* com.example.LibraryManagementSystem.Controller.BookController.updateBook(..)) || " +
            "execution(* com.example.LibraryManagementSystem.Controller.BorrowingRecordController.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} executed in {} ms.", methodName, executionTime);
        return result;
    }
}