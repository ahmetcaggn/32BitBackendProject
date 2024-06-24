package com.toyota.report.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class AspectLogger {

    @Before("execution(* com.toyota.report.service.ReportService.*(..)) && !@annotation(com.toyota.report.config.annotation.NoAspectLogging)")
    public void beforeMethodCall(JoinPoint joinPoint) {
        log.trace("{} - {} start...", joinPoint.toShortString(), Arrays.toString(joinPoint.getArgs()));
    }
    @After("execution(* com.toyota.report.service.ReportService.*(..)) && !@annotation(com.toyota.report.config.annotation.NoAspectLogging)")
     public void afterMethodCall(JoinPoint joinPoint) {
        log.trace("{} - {} end...", joinPoint.toShortString(), Arrays.toString(joinPoint.getArgs()));
    }

}
