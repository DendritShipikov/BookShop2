package com.dendrit.bookshop.audit.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
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
}
