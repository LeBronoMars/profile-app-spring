package com.sanmateo.profile.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class CreateNewsDto {

    @ApiModelProperty(example = "News Title 1")
    private String title;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String body;

    @JsonProperty("image_url")
    @ApiModelProperty(example = "http://www.inquirer.net/inq2016/images/inquirerdotnet_2016.svg")
    private String imageUrl;

    @JsonProperty("source_url")
    private String sourceUrl;
}
