package com.sanmateo.profile.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.enums.Status;
import com.sanmateo.profile.enums.UserRole;
import lombok.Data;

import java.util.Date;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class AppUserDto {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    private String address;

    @JsonProperty("contact_no")
    private String contactNo;

    private String email;
    private String username;
    private UserRole role;
    private Status status;

    @JsonProperty("pic_url")
    private String picUrl;
    private String gender;

    @JsonProperty("is_synced")
    private boolean isSynced;
}
