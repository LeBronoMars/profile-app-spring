package com.sanmateo.profile.dto.gallery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.dto.user.SimplifiedAppUserDto;
import com.sanmateo.profile.enums.Status;
import lombok.Data;

import java.util.Date;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class GalleryDto {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    private String title;

    @JsonProperty("image_url")
    private String imageUrl;

    private String description;

    private Status status;

    @JsonProperty("reported_by")
    private SimplifiedAppUserDto createdBy;

    @JsonProperty("updated_by")
    private SimplifiedAppUserDto updatedBy;

}
