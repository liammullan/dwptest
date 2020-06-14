package com.ometa.dwptest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class Controller {

    @Resource
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/londonusers")
    public List<User> getLondonUsers() {
        return service.getLondonUsers();
    }

}
