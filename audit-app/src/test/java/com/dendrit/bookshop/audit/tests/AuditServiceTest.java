package com.dendrit.bookshop.audit.tests;

import com.dendrit.bookshop.audit.entities.AuditItem;
import com.dendrit.bookshop.audit.repositories.AuditItemRepository;
import com.dendrit.bookshop.audit.services.AuditServiceImpl;
import com.dendrit.bookshop.common.audit.data.ExecutionTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

public class AuditServiceTest {

    AuditItemRepository auditItemRepository = Mockito.mock(AuditItemRepository.class);

    AuditServiceImpl auditService;

    @BeforeEach
    public void setup() {
        auditService = new AuditServiceImpl();
        auditService.setAuditItemRepository(auditItemRepository);
    }

    @Test
    public void saveExecutionTimeTest() {
        ExecutionTime executionTime = new ExecutionTime("name", 12l, new Date());
        AuditItem auditItem = new AuditItem();
        auditItem.setId(executionTime.getDate());
        auditItem.setDuration(executionTime.getDuration());
        auditItem.setName(executionTime.getName());
        auditService.saveExecutionTime(executionTime);
        Mockito.verify(auditItemRepository).save(auditItem);
    }

    @Test
    public void calculateAverageTest() {
        AuditItem auditItem1 = new AuditItem();
        auditItem1.setDuration(12L);
        AuditItem auditItem2 = new AuditItem();
        auditItem2.setDuration(6L);
        List<AuditItem> auditItems = List.of(auditItem1, auditItem2);
        Mockito.when(auditItemRepository.findAll()).thenReturn(auditItems);
        Assertions.assertEquals(9L, auditService.calculateAverage());
        Mockito.verify(auditItemRepository).findAll();
    }

}
