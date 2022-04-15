package com.dendrit.bookshop.audit.repositories;

import com.dendrit.bookshop.audit.entities.AuditItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AuditItemRepository extends JpaRepository<AuditItem, Date> {
}
