package com.ometa.dwptest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Service {

    private LondonUserProvider userProvider;

    @Autowired
    public Service(LondonUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public List<User> getLondonUsers(){
        return userProvider.getAllLondonUsers();
    }

}
