package com.sanmateo.profile.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by rsbulanon on 6/19/17.
 */
@Entity
@Data
public class Person extends BaseModel {

    @Column(nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Column(nullable = false)
    @JsonProperty("birth_day")
    private String birthDay;

    @Column(nullable = false, unique = true)
    @JsonProperty("email")
    private String email;

    @Column(nullable = false)
    @JsonProperty("mobile_number")
    private String mobileNumber;
}
