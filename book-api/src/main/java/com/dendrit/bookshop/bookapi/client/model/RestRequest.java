package com.dendrit.bookshop.bookapi.client.model;

import java.util.Map;

public class RestRequest<T> {

    private Map<String, String> headers;

    private  T body;

    private  String url;

    private  String method;

    public RestRequest(String method, String url, Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
        this.url = url;
        this.method = method;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
