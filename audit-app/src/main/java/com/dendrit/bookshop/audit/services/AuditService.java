package com.dendrit.bookshop.audit.services;

import com.dendrit.bookshop.common.audit.data.ExecutionTime;

public interface AuditService {

    void saveExecutionTime(ExecutionTime executionTime);

    int calculateAverage();

}
