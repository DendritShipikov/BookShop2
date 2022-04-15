package com.dendrit.bookshop.common.audit.data;

import java.util.Date;

public class ExecutionTime {
    private String name;
    private long duration;
    private Date date;

    public ExecutionTime() {}

    public ExecutionTime(String name, long duration, Date date) {
        this.name = name;
        this.duration = duration;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExecutionTime{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
