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
public class Announcement extends BaseModel {

    @Column(nullable = false)
    @ApiModelProperty(example = "News Title 1")
    private String title;

    @Column(nullable = false)
    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String message;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser createdBy;
}
