package com.toyota.Sales.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class AspectLogger {

    @Pointcut("execution(* com.toyota.Sales.service.SalesService.*(..)) || execution(* com.toyota.Sales.controller.SaleController.*(..))")
    private void salesMethods() {}

    @Pointcut("!@annotation(com.toyota.Sales.config.annotation.NoAspectLogging)")
    private void notNoAspectLogging() {}

    @Before("salesMethods() && notNoAspectLogging()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        log.trace("{} - {} start...", joinPoint.toShortString(), Arrays.toString(joinPoint.getArgs()));
    }

    @After("salesMethods() && notNoAspectLogging()")
    public void afterMethodCall(JoinPoint joinPoint) {
        log.trace("{} - {} end...", joinPoint.toShortString(), Arrays.toString(joinPoint.getArgs()));
    }

}
