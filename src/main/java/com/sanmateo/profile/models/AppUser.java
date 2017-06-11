package com.sanmateo.profile.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.enums.Status;
import com.sanmateo.profile.enums.UserRole;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Data
@Entity
public class AppUser extends BaseModel {

    @Column(nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Column(nullable = false)
    @JsonProperty("middle_name")
    private String middleName;

    @Column(nullable = false)
    @JsonProperty("address")
    private String address;

    @Column(nullable = false)
    @JsonProperty("contact_no")
    private String contactNo;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(columnDefinition = "CHAR(15)", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonProperty("pic_url")
    private String picUrl;

    @Column(columnDefinition = "CHAR(6)", length = 6)
    private String gender;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
