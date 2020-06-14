package com.ometa.dwptest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class LondonUserProvider {

    private final UserSource userSource;
    private final DistanceCalculator distanceCalculator;
    private final double londonLatitude;
    private final double londonLongitude;

    static final int MAX_DISTANCE = 50;

    @Autowired
    LondonUserProvider(UserSource userSource,
                       DistanceCalculator distanceCalculator,
                       @Value("${london.latitude}") double londonLatitude,
                       @Value("${london.longitude}") double londonLongitude) {
        this.userSource = userSource;
        this.distanceCalculator = distanceCalculator;
        this.londonLatitude = londonLatitude;
        this.londonLongitude = londonLongitude;
    }

    List<User> getAllLondonUsers(){
        Set<User> users = new HashSet<>();
        users.addAll(userSource.getExplicitLondonUsers());
        users.addAll(getImplicitLondonUsers());
        return getFullRecords(users);
    }

    private List<User> getFullRecords(Set<User> users) {
        return users.stream()
                .map(u -> u.getId())
                .map(id -> userSource.getUser(id))
                .collect(toList());
    }

    private List<User> getImplicitLondonUsers(){
        return userSource.getAllUsers().stream().filter(this::isCloseToLondon).collect(toList());
    }

    private boolean isCloseToLondon(User user) {
        if(user.getLatitude() == null || user.getLongitude() == null){
            return false;
        }
        double distanceToLondon = distanceCalculator.calculateDistanceInMiles(londonLatitude, londonLongitude, user.getLatitude(), user.getLongitude());
        return distanceToLondon < MAX_DISTANCE;
    }

}
