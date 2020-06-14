package com.ometa.dwptest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.ometa.dwptest.Controller.LONDON_USERS_PATH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void canGetLondonUsers() throws Exception {
		String url = "http://localhost:" + port + "/" + LONDON_USERS_PATH;
		ResponseEntity<List> response = this.restTemplate.getForEntity(url, List.class);
		List<User> users = response.getBody();
		assertThat(users, notNullValue());
		assertThat(users.size(), is(greaterThan(1)));
	}

}
