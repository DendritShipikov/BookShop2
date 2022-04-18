package com.dendrit.bookshop.common.audit.aspects;

import com.dendrit.bookshop.common.audit.data.ExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class CalculateTimeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateTimeAspect.class);

    private KafkaTemplate<String, ExecutionTime> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(calculateTime)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, CalculateTime calculateTime) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        ExecutionTime executionTime = new ExecutionTime(calculateTime.name(), endTime - startTime, new Date());
        String msg = "Method '" + executionTime.getName() + "' takes " + executionTime.getDuration()  + "ms";
        LOGGER.info(msg);
        kafkaTemplate.send("audit", executionTime);
        return result;
    }

}
