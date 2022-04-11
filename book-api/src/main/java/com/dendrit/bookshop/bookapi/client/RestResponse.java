package com.dendrit.bookshop.bookapi.client;

import java.util.Map;

public class RestResponse<T> {

    private final Map<String, String> headers;

    private final T body;

    private final int statusCode;

    public RestResponse(int statusCode, Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
