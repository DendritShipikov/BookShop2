package com.dendrit.bookshop.common.audit.data;

public class ExecutionTime {
    private String name;
    private long duration;

    public ExecutionTime() {}

    public ExecutionTime(String name, long duration) {
        this.name = name;
        this.duration = duration;
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
}
