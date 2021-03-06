package com.sanmateo.profile.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class SimplifiedAppUserDto {

    private String id;

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

    private Status status;
}
