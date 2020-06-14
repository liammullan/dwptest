package com.ometa.dwptest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
class UserSource {

    private final String url;
    private final RestTemplate restTemplate;

    static final String LONDON_USERS_END_POINT = "/city/London/users";
    static final String USERS_END_POINT = "users";
    static final String USER_END_POINT = "user/";

    UserSource(@Value("${source.api.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    List<User> getAllUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url + USERS_END_POINT, User[].class);
        return getUserListFromResponse(responseEntity);
    }

    List<User> getExplicitLondonUsers(){
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url + LONDON_USERS_END_POINT, User[].class);
        return getUserListFromResponse(responseEntity);
    }

    private List<User> getUserListFromResponse(ResponseEntity<User[]> responseEntity) {
        User[] result = responseEntity.getBody();
        return result == null ? new ArrayList<>() : Arrays.asList(result);
    }

    public User getUser(String id) {
        return restTemplate.getForObject(url + USER_END_POINT + id, User.class);
    }
}
