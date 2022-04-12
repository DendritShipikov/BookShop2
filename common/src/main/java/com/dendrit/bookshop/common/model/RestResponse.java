package com.dendrit.bookshop.common.model;

import java.util.Map;

public class RestResponse<T> {

    private Map<String, String> headers;

    private T body;

    private int statusCode;

    public RestResponse(int statusCode, Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
