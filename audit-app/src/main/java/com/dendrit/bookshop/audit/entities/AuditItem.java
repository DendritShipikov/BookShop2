package com.dendrit.bookshop.audit.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "audit_items")
public class AuditItem {

    @Id
    private Date id;

    private String name;

    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getId() {
        return id;
    }

    public void setId(Date id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String desc) {
        this.name = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditItem auditItem = (AuditItem) o;
        return duration == auditItem.duration && Objects.equals(id, auditItem.id) && Objects.equals(name, auditItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration);
    }
}
