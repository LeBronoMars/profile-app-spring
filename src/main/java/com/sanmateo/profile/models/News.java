package com.sanmateo.profile.models;

import com.sanmateo.profile.enums.NewsStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Entity
@Data
public class News extends BaseModel {

    @Column(nullable = false)
    @ApiModelProperty(example = "News Title 1")
    private String title;

    @Column(nullable = false)
    @ApiModelProperty(example = "Lorem ipsum dolor")
    @Lob
    private String body;

    @Column(nullable = false)
    private String imageUrl;

    private String sourceUrl;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser reportedBy;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser updatedBy;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    @ApiModelProperty(example = "Active", allowableValues = "ACTIVE, ARCHIVED")
    @Enumerated(EnumType.STRING)
    private NewsStatus status;
}
