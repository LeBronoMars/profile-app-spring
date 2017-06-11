package com.sanmateo.profile.dto.waterlevel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmateo.profile.dto.user.SimplifiedAppUserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class WaterMonitoringDto {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    private String area;

    private float level;

    private String alert;

    @JsonProperty("created_by")
    private SimplifiedAppUserDto createdBy;
}
