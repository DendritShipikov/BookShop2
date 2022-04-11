package com.dendrit.bookshop.bookapi.client;

import java.util.Map;

public class RestRequest<T> {

    private final Map<String, String> headers;

    private final T body;

    private final String url;

    private final String method;

    public RestRequest(String method, String url, Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
        this.url = url;
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}
