package com.sanmateo.profile.dto.announcement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class CreateAnnouncementDto {

    @ApiModelProperty(example = "Hello World")
    private String title;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String message;
}
