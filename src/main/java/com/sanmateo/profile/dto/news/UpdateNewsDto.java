package com.sanmateo.profile.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.enums.NewsStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class UpdateNewsDto {

    @ApiModelProperty(example = "News Title 1")
    private String title;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String body;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("source_url")
    private String sourceUrl;

    @ApiModelProperty(example = "ACTIVE", allowableValues = "ACTIVE, ARCHIVED")
    private NewsStatus status;
}
