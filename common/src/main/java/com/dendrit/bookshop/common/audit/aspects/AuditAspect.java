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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class AuditAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);

    private KafkaTemplate<String, ExecutionTime> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Around("@annotation(audit)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, Audit audit) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String name = request.getMethod() + " " + request.getRequestURL();
        ExecutionTime executionTime = new ExecutionTime(name, endTime - startTime, new Date());
        String msg = "Request '" + name + "' takes " + executionTime.getDuration()  + "ms";
        LOGGER.info(msg);
        kafkaTemplate.send("audit", executionTime);
        return result;
    }

}
