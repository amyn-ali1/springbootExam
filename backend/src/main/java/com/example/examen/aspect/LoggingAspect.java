package com.example.examen.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.examen.controller.EmployeeController.*(..))")
    public void employeeControllerMethods() {}


    @Before("employeeControllerMethods()")
    public void logBeforeEmployeeOperation(JoinPoint joinPoint) {
        logger.info("Attempting operation: {} with args: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "employeeControllerMethods()", returning = "result")
    public void logAfterEmployeeOperation(JoinPoint joinPoint, Object result) {
        logger.info("Completed operation: {} with result: {}",
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(pointcut = "employeeControllerMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Error in method: {} with cause: {}",
                joinPoint.getSignature().getName(),
                error.getMessage());
    }
}
