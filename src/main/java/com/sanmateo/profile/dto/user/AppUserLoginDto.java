package com.sanmateo.profile.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class AppUserLoginDto {

    @ApiModelProperty(example = "nedflanders")
    private String username;

    @ApiModelProperty(example = "P@ssw0rd")
    private String password;
}
