package com.dendrit.bookshop.audit.controllers;

import com.dendrit.bookshop.audit.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {

    private AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/average")
    public int average() {
        return auditService.calculateAverage();
    }
}
