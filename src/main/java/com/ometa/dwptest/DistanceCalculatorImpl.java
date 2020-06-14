package com.ometa.dwptest;

import org.springframework.stereotype.Component;

/** Derived from geodatasource sample: https://www.geodatasource.com/developers/java */
@Component
public class DistanceCalculatorImpl implements DistanceCalculator {

    @Override
    public double calculateDistanceInMiles(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }
}
