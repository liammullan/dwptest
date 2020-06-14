package com.ometa.dwptest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Getter
    private String id;
    @Getter
    @JsonProperty("first_name")
    private String firstName;
    @Getter
    @JsonProperty("last_name")
    private String lastName;
    @Getter
    private String email;
    @Getter
    private Double latitude;
    @Getter
    private Double longitude;
    @Getter
    private String city;
}
