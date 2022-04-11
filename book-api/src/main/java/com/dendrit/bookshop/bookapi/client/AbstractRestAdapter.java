package com.dendrit.bookshop.bookapi.client;

public abstract class AbstractRestAdapter {

    public abstract <T, D> RestResponse<T> execute(RestRequest<D> restRequest, Class<T> responseType);

}
