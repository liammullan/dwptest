package com.ometa.dwptest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class ServiceIntTest {

    @Resource
    private Service underTest;

    @Test
    public void canRetrieveLondonUsers(){
        // given
        // when
        List<User> londonUsers = underTest.getLondonUsers();
        // then
        assertThat(londonUsers, notNullValue());
        assertThat(londonUsers.size(), greaterThan(1));
    }

}