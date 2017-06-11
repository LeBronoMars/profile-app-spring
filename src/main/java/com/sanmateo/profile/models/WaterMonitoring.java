package com.sanmateo.profile.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Entity
@Data
public class WaterMonitoring extends BaseModel {

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private float level;

    @Column(nullable = false)
    private String alert;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser createdBy;
}
