package com.dendrit.bookshop.audit.services;

import com.dendrit.bookshop.audit.entities.AuditItem;
import com.dendrit.bookshop.audit.repositories.AuditItemRepository;
import com.dendrit.bookshop.common.audit.data.ExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuditServiceImpl implements AuditService {

    private AuditItemRepository auditItemRepository;

    @Autowired
    public void setAuditItemRepository(AuditItemRepository auditItemRepository) {
        this.auditItemRepository = auditItemRepository;
    }

    @Override
    @Transactional
    public void saveExecutionTime(ExecutionTime executionTime) {
        AuditItem auditItem = new AuditItem();
        auditItem.setDuration(executionTime.getDuration());
        auditItem.setName(executionTime.getName());
        auditItem.setId(executionTime.getDate());
        auditItemRepository.save(auditItem);
    }

    @Override
    @Transactional
    public int calculateAverage() {
        Iterable<AuditItem> auditItems = auditItemRepository.findAll();
        int totalCount = 0;
        int totalSum = 0;
        for (AuditItem auditItem : auditItems) {
            totalSum += auditItem.getDuration();
            ++totalCount;
        }
        return totalSum / totalCount;
    }
}
