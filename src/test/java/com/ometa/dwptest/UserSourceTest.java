package com.ometa.dwptest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.ometa.dwptest.TestUtils.anyString;
import static com.ometa.dwptest.UserSource.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSourceTest {

    private String baseUrl;
    private ArgumentCaptor<String> urlCaptor;
    @Mock private RestTemplate restTemplate;
    private UserSource underTest;

    @BeforeEach
    void setup(){
        urlCaptor = ArgumentCaptor.forClass(String.class);
        baseUrl = anyString();
        underTest = new UserSource(baseUrl, restTemplate);
    }

    @Test
    void canGetUser() {
        // given
        User expectedUser = new User();
        String id = anyString();
        when(restTemplate.getForObject(urlCaptor.capture(), eq(User.class))).thenReturn(expectedUser);
        // when
        User actualUser = underTest.getUser(id);
        // then
        String url = urlCaptor.getValue();
        assertThat(url, Matchers.stringContainsInOrder(baseUrl, USER_END_POINT, id));
        assertThat(actualUser, is(expectedUser));
    }

    @Test
    void canGetAllUsers() {
        // given
        User[] expectedUsers = new User[]{};
        ResponseEntity<User[]> usersResponseEntity = mock(ResponseEntity.class);
        when(restTemplate.getForEntity(urlCaptor.capture(), eq(User[].class))).thenReturn(usersResponseEntity);
        when(usersResponseEntity.getBody()).thenReturn(expectedUsers);
        // when
        List<User> actualUsers = underTest.getAllUsers();
        // then
        String url = urlCaptor.getValue();
        assertThat(url, Matchers.stringContainsInOrder(baseUrl, USERS_END_POINT));
        assertThat(actualUsers.toArray(), is(expectedUsers));
    }

    @Test
    void canGetExplicitLondonUsers() {
        // given
        User[] expectedUsers = new User[]{};
        ResponseEntity<User[]> usersResponseEntity = mock(ResponseEntity.class);
        when(restTemplate.getForEntity(urlCaptor.capture(), eq(User[].class))).thenReturn(usersResponseEntity);
        when(usersResponseEntity.getBody()).thenReturn(expectedUsers);
        // when
        List<User> actualUsers = underTest.getExplicitLondonUsers();
        // then
        String url = urlCaptor.getValue();
        assertThat(url, Matchers.stringContainsInOrder(baseUrl, LONDON_USERS_END_POINT));
        assertThat(actualUsers.toArray(), is(expectedUsers));
    }

}