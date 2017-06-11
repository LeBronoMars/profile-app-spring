package com.sanmateo.profile.dto.waterlevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Data
public class CreateWaterMonitoringDto {

    @ApiModelProperty(example = "Bayan Palengke")
    private String area;

    @ApiModelProperty(example = "18.0")
    private float level;
}
