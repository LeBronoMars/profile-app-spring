package com.sanmateo.profile.dto.gallery;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class CreateGalleryDto {

    @ApiModelProperty(example = "Gallery 1")
    private String title;

    @JsonProperty("image_url")
    @ApiModelProperty(example = "https://pbs.twimg.com/profile_images/1410357501/SMR_Logo.jpg")
    private String imageUrl;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String description;
}
