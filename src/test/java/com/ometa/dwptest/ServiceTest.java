package com.ometa.dwptest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock private LondonUserProvider userProvider;
    private Service underTest;

    @BeforeEach
    void setup(){
        underTest = new Service(userProvider);
    }

    @Test
    void canGetLondonUsers() {
        // given
        List<User> expectedUsers = new ArrayList<>();
        when(userProvider.getAllLondonUsers()).thenReturn(expectedUsers);
        // when
        List<User> actualUsers = underTest.getLondonUsers();
        // then
        assertThat(actualUsers, is(expectedUsers));
    }
}