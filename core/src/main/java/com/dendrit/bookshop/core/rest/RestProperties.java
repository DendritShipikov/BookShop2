package com.dendrit.bookshop.core.rest;

import org.springframework.core.io.Resource;

public interface RestProperties {

    String getBaseAddress();

    Resource getTrustStore();

    String getTrustStorePassword();

}
