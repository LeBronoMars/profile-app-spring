package com.sanmateo.profile.controllers;


import com.sanmateo.profile.dto.announcement.AnnouncementDto;
import com.sanmateo.profile.dto.announcement.CreateAnnouncementDto;
import com.sanmateo.profile.enums.UserRole;
import com.sanmateo.profile.exceptions.CustomException;
import com.sanmateo.profile.models.Announcement;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.services.AnnouncementService;
import com.sanmateo.profile.services.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Controller
@RequestMapping(path = "/api")
@Api(value = "Announcement Module")
public class AnnouncementController {

    private final Logger log = LoggerFactory.getLogger(AnnouncementController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AnnouncementService announcementService;

    /**
     * create announcement
     */
    @RequestMapping(value = "/announcement",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAnnouncement(@Valid @RequestBody CreateAnnouncementDto createAnnouncementDto, HttpServletRequest request) {
        log.info("REST request to create announcement : {}", createAnnouncementDto);
        return Optional.ofNullable(request.getRemoteUser()).map(user -> {
            final AppUser appUser = appUserService.findByUsername(user);
            try {
                if (appUser.getRole().equals(UserRole.REGULAR_USER)) {
                    return new ResponseEntity<>(Collections.singletonMap("message", "You are not allowed to access this resource."), HttpStatus.BAD_REQUEST);
                } else {
                    final Announcement announcement = announcementService.createAnnouncement(appUser, createAnnouncementDto);
                    final AnnouncementDto announcementDto = announcementService.convert(announcement);
                    return ResponseEntity.created(new URI("/api/news/" + announcement.getId())).body(announcementDto);
                }
            } catch (CustomException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            } catch (URISyntaxException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            }
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    /**
     * get all announcement
     */
    @RequestMapping(value = "/announcements",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<Page<AnnouncementDto>> getAnnouncements(Pageable pageable) throws URISyntaxException {
        final Page<AnnouncementDto> announcementDtos = announcementService.findAll(pageable).map(source -> announcementService.convert(source));
        return new ResponseEntity<>(announcementDtos, HttpStatus.OK);
    }

    /**
     * get announcement by id
     * */
    @RequestMapping(value = "/announcement/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAnnouncementById(@PathVariable String id) {
        try {
            final Announcement announcement = announcementService.findOne(id);

            if (announcement == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                final AnnouncementDto announcementDto = announcementService.convert(announcement);
                return ResponseEntity.ok().body(announcementDto);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete announcement
     * */
    @RequestMapping(value = "/announcement/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAnnouncement(@PathVariable String id) {
        try {
            final Announcement announcement = announcementService.findOne(id);

            if (announcement == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                announcementService.delete(announcement);
                return new ResponseEntity<>(Collections.singletonMap("message", "Announcement successfully deleted."), HttpStatus.OK);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
