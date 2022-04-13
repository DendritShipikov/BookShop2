package com.dendrit.bookshop.bookapi.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CalculateTimeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateTimeAspect.class);

    @Pointcut("execution(* com.dendrit.bookshop.bookapi.controllers.*.*(..))")
    public void inControllerLayer() {}

    @Around("@annotation(com.dendrit.bookshop.bookapi.aspects.CalculateTime)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        LOGGER.info("Method '" + proceedingJoinPoint.getSignature() + "' takes " + (endTime -startTime) + "ms");
        return result;
    }

}
