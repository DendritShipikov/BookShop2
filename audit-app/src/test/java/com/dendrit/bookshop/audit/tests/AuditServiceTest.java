package com.dendrit.bookshop.audit.tests;

import com.dendrit.bookshop.audit.entities.AuditItem;
import com.dendrit.bookshop.audit.repositories.AuditItemRepository;
import com.dendrit.bookshop.audit.services.AuditServiceImpl;
import com.dendrit.bookshop.common.audit.data.ExecutionTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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
        ExecutionTime executionTime = new ExecutionTime("name", 12L, new Date());
        AuditItem auditItem = new AuditItem();
        auditItem.setId(executionTime.getDate());
        auditItem.setDuration(executionTime.getDuration());
        auditItem.setName(executionTime.getName());
        auditService.saveExecutionTime(executionTime);
        Mockito.verify(auditItemRepository).save(auditItem);
    }

    @ParameterizedTest
    @MethodSource("listAverageProvider")
    public void calculateAverageTest(List<AuditItem> auditItems, long avg) {
        Mockito.when(auditItemRepository.findAll()).thenReturn(auditItems);
        Assertions.assertEquals(avg, auditService.calculateAverage());
        Mockito.verify(auditItemRepository).findAll();
    }

    static Stream<Arguments> listAverageProvider() {
        AuditItem auditItem1 = new AuditItem();
        auditItem1.setDuration(12L);
        AuditItem auditItem2 = new AuditItem();
        auditItem2.setDuration(6L);
        AuditItem auditItem3 = new AuditItem();
        auditItem3.setDuration(6L);
        return Stream.of(
                Arguments.arguments(List.of(auditItem1, auditItem2), 9L),
                Arguments.arguments(List.of(auditItem1, auditItem2, auditItem3), 8L)
        );
    }

}
