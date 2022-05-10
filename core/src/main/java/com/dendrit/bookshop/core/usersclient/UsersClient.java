package com.dendrit.bookshop.core.usersclient;

import com.dendrit.bookshop.core.rest.AbstractRestAdapter;
import com.dendrit.bookshop.core.rest.RestProperties;
import com.dendrit.bookshop.core.rest.data.RestRequest;
import com.dendrit.bookshop.core.rest.data.RestResponse;
import com.dendrit.bookshop.core.security.AuthorizationDataProvider;
import com.dendrit.bookshop.core.security.data.AuthorizationData;
import com.dendrit.bookshop.core.usersclient.data.UserData;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class UsersClient extends AbstractRestAdapter implements AuthorizationDataProvider {

    private RestTemplate restTemplate;

    private UsersClientProperties usersClientProperties;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setUsersClientProperties(UsersClientProperties usersClientProperties) {
        this.usersClientProperties = usersClientProperties;
    }

    @Override
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    protected RestProperties getRestProperties() {
        return usersClientProperties;
    }

    public UserData getUserById(Long id) {
        RestRequest<Void> restRequest = new RestRequest<>(HttpMethod.GET.name(), "/" + id, new HashMap<>(), null);
        RestResponse<UserData> restResponse = super.execute(restRequest, UserData.class);
        return restResponse.getBody();
    }

    @Override
    public AuthorizationData getAuthorizationData(Long id) {
        return getUserById(id);
    }
}
