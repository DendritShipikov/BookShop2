package com.dendrit.bookshop.common.absrtact;

import org.springframework.core.io.Resource;

public interface RestProperties {

    String getBaseAddress();

    Resource getTrustStore();

    String getTrustStorePassword();

}
