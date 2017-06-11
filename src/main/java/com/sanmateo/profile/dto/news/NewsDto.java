package com.sanmateo.profile.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.dto.user.SimplifiedAppUserDto;
import lombok.Data;

import java.util.Date;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class NewsDto {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    private String title;

    private String body;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("source_url")
    private String sourceUrl;

    @JsonProperty("reported_by")
    private SimplifiedAppUserDto reportedBy;

    @JsonProperty("updated_by")
    private SimplifiedAppUserDto updatedBy;

}
