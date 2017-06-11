package com.sanmateo.profile.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Data
@Entity
public class AppUser extends BaseModel {

    @Column(nullable = false)
    @JsonProperty("first_name")
    @ApiModelProperty(example = "Ned")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    @ApiModelProperty(example = "Johnson")
    private String lastName;

    @Column(nullable = false)
    @JsonProperty("middle_name")
    @ApiModelProperty(example = "Flanders")
    private String middleName;

    @Column(nullable = false)
    @JsonProperty("address")
    @ApiModelProperty(example = "Hagonoy, Bulacan")
    private String address;

    @Column(nullable = false)
    @JsonProperty("contact_no")
    @ApiModelProperty(example = "09123456789")
    private String contactNo;

    @Column(unique = true, nullable = false)
    @ApiModelProperty(example = "ned@flanders.com")
    private String email;

    @Column(unique = true, nullable = false)
    @ApiModelProperty(example = "nedflanders")
    private String username;

    @Column(columnDefinition = "CHAR(15)", length = 30, nullable = false)
    @ApiModelProperty(example = "SUPER_ADMIN", allowableValues = "REGULAR_USER, ADMIN, SUPER_ADMIN")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    @ApiModelProperty(example = "P@ssw0rd")
    private String password;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    private String status;

    @Column(nullable = false)
    @JsonProperty("pic_url")
    private String picUrl;

    @Column(columnDefinition = "CHAR(6)", length = 6)
    @NotNull(message = "gender is required.")
    @ApiModelProperty(example = "Male")
    private String gender;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
