package com.dendrit.bookshop.common.audit.aspects;

import com.dendrit.bookshop.common.audit.data.ExecutionTime;
import org.apache.kafka.clients.admin.NewTopic;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Configuration
public class CalculateTimeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateTimeAspect.class);

    private KafkaTemplate<String, ExecutionTime> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(com.dendrit.bookshop.common.audit.aspects.CalculateTime)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        CalculateTime calculateTime = method.getAnnotation(CalculateTime.class);
        ExecutionTime executionTime = new ExecutionTime(calculateTime.name(), endTime - startTime);
        String msg = "Method '" + executionTime.getName() + "' takes " + executionTime.getDuration()  + "ms";
        LOGGER.info(msg);
        kafkaTemplate.send("audit", executionTime);
        return result;
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("audit").partitions(2).replicas(1).build();
    }

}
