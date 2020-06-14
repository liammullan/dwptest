package com.ometa.dwptest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.ometa.dwptest.LondonUserProvider.MAX_DISTANCE;
import static com.ometa.dwptest.TestUtils.anyDouble;
import static com.ometa.dwptest.TestUtils.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LondonUserProviderTest {

    @Mock private UserSource userSource;
    @Mock private DistanceCalculator distanceCalculator;
    private double londonLatitude, londonLongitude;
    private double nearbyLatitude, nearbyLongitude;
    private double distantLatitude, distantLongitude;
    private String londonUserId, nearbyUserId, distantUserId;
    private User londonUserPartial, londonUserFull, nearbyUserPartial, nearbyUserFull, distantUserPartial, distantUserFull;
    private LondonUserProvider underTest;

    @BeforeEach
    public void setup(){
        londonLatitude = anyDouble();
        londonLongitude = anyDouble();
        nearbyLatitude = anyDouble();
        nearbyLongitude = anyDouble();
        distantLatitude = anyDouble();
        distantLongitude = anyDouble();
        londonUserId = anyString();
        nearbyUserId = anyString();
        distantUserId = anyString();
        londonUserPartial = User.builder().id(londonUserId).build();
        londonUserFull = User.builder().build();
        nearbyUserPartial = User.builder().id(nearbyUserId).latitude(nearbyLatitude).longitude(nearbyLongitude).build();
        nearbyUserFull = User.builder().build();
        distantUserPartial = User.builder().id(distantUserId).latitude(distantLatitude).longitude(distantLongitude).build();
        distantUserFull = User.builder().build();
        when(userSource.getAllUsers()).thenReturn(newArrayList(londonUserPartial, nearbyUserPartial, distantUserPartial));
        when(userSource.getExplicitLondonUsers()).thenReturn(newArrayList(londonUserPartial));
        when(userSource.getUser(londonUserId)).thenReturn(londonUserFull);
        when(distanceCalculator.calculateDistanceInMiles(londonLatitude, londonLongitude, distantLatitude, distantLongitude))
                .thenReturn(MAX_DISTANCE + 1.0);
        underTest = new LondonUserProvider(userSource, distanceCalculator, londonLatitude, londonLongitude);
    }

    @Test
    void canSelectUsersBasedOnDistance() {
        // given
        when(distanceCalculator.calculateDistanceInMiles(londonLatitude, londonLongitude, nearbyLatitude, nearbyLongitude))
                .thenReturn(MAX_DISTANCE - 1.0);
        when(userSource.getUser(nearbyUserId)).thenReturn(nearbyUserFull);
        //when
        List<User> allLondonUsers = underTest.getAllLondonUsers();
        //then
        assertThat(allLondonUsers.size(), is(2));
        assertThat(allLondonUsers, hasItems(nearbyUserFull, londonUserFull));
    }

    @Test
    void canHandleNullCoordinates() {
        //given
        nearbyUserPartial = User.builder().id(nearbyUserId).latitude(null).longitude(null).build();
        when(userSource.getAllUsers()).thenReturn(newArrayList(londonUserPartial, nearbyUserPartial, distantUserPartial));
        //when
        List<User> allLondonUsers = underTest.getAllLondonUsers();
        //then
        assertThat(allLondonUsers.size(), is(1));
        assertThat(allLondonUsers, hasItems(londonUserFull));

    }

}