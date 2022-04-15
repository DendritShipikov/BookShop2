package com.dendrit.bookshop.audit.consumer;

import com.dendrit.bookshop.audit.services.AuditService;
import com.dendrit.bookshop.common.audit.data.ExecutionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(topics = "audit", groupId = "audit-app")
    public void consume(ExecutionTime executionTime) {
        LOGGER.info("consumed message: " + executionTime.toString());
        auditService.saveExecutionTime(executionTime);
    }

}
