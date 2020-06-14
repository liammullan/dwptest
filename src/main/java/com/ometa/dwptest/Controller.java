package com.ometa.dwptest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class Controller {

    final static String LONDON_USERS_PATH = "londonusers";

    @Resource
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/" + LONDON_USERS_PATH)
    public List<User> getLondonUsers() {
        return service.getLondonUsers();
    }

}
