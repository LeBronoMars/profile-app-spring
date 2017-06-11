package com.sanmateo.profile.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class SimplifiedAppUserDto {

    private String id;

    @JsonProperty("employee_no")
    private String employeeNo;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("contact_no")
    private String contactNo;

    private String email;

    @JsonProperty("pic_url")
    private String picUrl;

}
