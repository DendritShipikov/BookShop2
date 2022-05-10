package com.dendrit.bookshop.notificationapi.data;

public class NotificationRequest {

    private Long id;

    private String text;

    public NotificationRequest() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
