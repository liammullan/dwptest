package com.ometa.dwptest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @Mock
    private Service service;

    @Test
    void willInvokeService() {
        // given
        List<User> expectedUsers = new ArrayList<>();
        Controller controller = new Controller(service);
        when(service.getLondonUsers()).thenReturn(expectedUsers);
        // when
        List<User> actualUsers = controller.getLondonUsers();
        // then
        assertThat(actualUsers, is(expectedUsers));
    }
}