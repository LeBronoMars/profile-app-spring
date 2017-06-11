package com.sanmateo.profile.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.enums.Status;
import com.sanmateo.profile.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class UpdateUserDto {

    @JsonProperty("first_name")
    @ApiModelProperty(example = "Ned")
    private String firstName;

    @JsonProperty("last_name")
    @ApiModelProperty(example = "Johnson")
    private String lastName;

    @JsonProperty("middle_name")
    @ApiModelProperty(example = "Flanders")
    private String middleName;

    @JsonProperty("address")
    @ApiModelProperty(example = "Hagonoy, Bulacan")
    private String address;

    @JsonProperty("contact_no")
    @ApiModelProperty(example = "09123456789")
    private String contactNo;

    @ApiModelProperty(example = "ned@flanders.com")
    private String email;

    @ApiModelProperty(example = "nedflanders")
    private String username;

    @ApiModelProperty(example = "SUPER_ADMIN", allowableValues = "REGULAR_USER, ADMIN, SUPER_ADMIN")
    private UserRole role;

    @ApiModelProperty(example = "P@ssw0rd")
    private String password;

    @JsonProperty("pic_url")
    private String picUrl;

    @ApiModelProperty(example = "Male", allowableValues = "Male, Female")
    private String gender;

    @ApiModelProperty(example = "Active", allowableValues = "Active, Inactive")
    private Status status;
}
