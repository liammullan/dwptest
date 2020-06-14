package com.ometa.dwptest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.lessThan;

class DistanceCalculatorImplTest {

    private final double manchesterLat = 53.4808;
    private final double manchesterLon = -2.2426;
    private final double liverpoolLat = 53.4084;
    private final double liverpoolLon = -2.9916;

    DistanceCalculatorImpl underTest;

    @BeforeEach
    void setup(){
        underTest = new DistanceCalculatorImpl();
    }

    @Test
    void canMeasureCloseDistanceAccurately() {
        // given
        double expectedDistance = 31.4; // measured on google maps
        double tolerance = expectedDistance * 0.01;
        // when
        double actualDistance = underTest.calculateDistanceInMiles(manchesterLat, manchesterLon, liverpoolLat, liverpoolLon);
        // then
        assertThat(actualDistance, closeTo(expectedDistance, tolerance));
    }

    @Test
    void canMeasureLongDistanceAccurately() {
        // given
        double expectedDistance = 1578; // measured on google maps
        double tolerance = expectedDistance * 0.01;
        // when
        double moscowLat = 55.7558;
        double moscowLon = 37.6173;
        double actualDistance = underTest.calculateDistanceInMiles(manchesterLat, manchesterLon, moscowLat, moscowLon);
        // then
        assertThat(actualDistance, closeTo(expectedDistance, tolerance));
    }

    @Test
    void measuredDistanceIsConsistentWithAlternativeCalculator() {
        // given
        HaversineDistanceCalculator haversineDistanceCalculator = new HaversineDistanceCalculator();
        double haversineDistance = haversineDistanceCalculator.calculateDistanceInMiles(manchesterLat, manchesterLon, liverpoolLat, liverpoolLon);
        double tolerance = haversineDistance * 0.0001;
        // when
        double actualDistance = underTest.calculateDistanceInMiles(manchesterLat, manchesterLon, liverpoolLat, liverpoolLon);
        // then
        double difference = Math.abs(haversineDistance - actualDistance);
        assertThat(difference, lessThan(tolerance));
    }
}