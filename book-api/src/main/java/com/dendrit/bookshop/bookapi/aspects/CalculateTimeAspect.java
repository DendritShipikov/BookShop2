package com.dendrit.bookshop.bookapi.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CalculateTimeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateTimeAspect.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(com.dendrit.bookshop.bookapi.aspects.CalculateTime)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        String msg = "Method '" + proceedingJoinPoint.getSignature() + "' takes " + (endTime -startTime) + "ms";
        LOGGER.info(msg);
        kafkaTemplate.send("audit", msg);
        return result;
    }

}
