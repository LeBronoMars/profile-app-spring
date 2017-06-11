package com.sanmateo.profile.controllers;


import com.sanmateo.profile.dto.gallery.CreateGalleryDto;
import com.sanmateo.profile.dto.gallery.GalleryDto;
import com.sanmateo.profile.dto.gallery.UpdateGalleryDto;
import com.sanmateo.profile.enums.Status;
import com.sanmateo.profile.exceptions.CustomException;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.models.Gallery;
import com.sanmateo.profile.services.AppUserService;
import com.sanmateo.profile.services.GalleryService;
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
import org.springframework.web.bind.annotation.*;

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
@Api(value = "Gallery Module")
public class GalleryController {

    private final Logger log = LoggerFactory.getLogger(GalleryController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private GalleryService galleryService;

    /**
     * create gallery
     */
    @RequestMapping(value = "/gallery",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNews(@Valid @RequestBody CreateGalleryDto createGalleryDto, HttpServletRequest request) {
        log.info("REST request to create Gallery : {}", createGalleryDto);
        return Optional.ofNullable(request.getRemoteUser()).map(user -> {
            final AppUser appUser = appUserService.findByUsername(user);
            try {
                final Gallery gallery = galleryService.createGallery(appUser, createGalleryDto);
                final GalleryDto galleryDto = galleryService.convert(gallery);
                return ResponseEntity.created(new URI("/api/galleries/" + gallery.getId())).body(galleryDto);
            } catch (CustomException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            } catch (URISyntaxException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            }
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    /**
     * update gallery
     */
    @RequestMapping(value = "/galleries/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateGallery(@PathVariable String id,
                                           @Valid @RequestBody UpdateGalleryDto updateGalleryDto, HttpServletRequest request) {
        log.info("REST request to update Gallery : {}", updateGalleryDto);
        return Optional.ofNullable(request.getRemoteUser()).map(user -> {
            final AppUser appUser = appUserService.findByUsername(user);
            try {
                final Gallery existingGallery = galleryService.findOne(id);

                if (existingGallery == null) {
                    return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
                } else {
                    final Gallery gallery = galleryService.updateGallery(appUser, existingGallery, updateGalleryDto);
                    return ResponseEntity.ok().body(galleryService.convert(gallery));
                }
            } catch (CustomException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            }
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    /**
     * get all galleries
     */
    @RequestMapping(value = "/galleries",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<Page<GalleryDto>> getGalleries(Pageable pageable, @RequestParam("status") Status status) throws URISyntaxException {
        final Page<GalleryDto> galleryDtos = galleryService.findByStatus(pageable, status).map(source -> galleryService.convert(source));
        return new ResponseEntity<>(galleryDtos, HttpStatus.OK);
    }

    /**
     * get gallery by id
     * */
    @RequestMapping(value = "/galleries/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGalleryById(@PathVariable String id) {
        try {
            final Gallery existingGallery = galleryService.findOne(id);

            if (existingGallery == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                final GalleryDto galleryDto = galleryService.convert(existingGallery);
                return ResponseEntity.ok().body(galleryDto);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete news
     * */
    @RequestMapping(value = "/galleries/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNews(@PathVariable String id) {
        try {
            final Gallery existingGallery = galleryService.findOne(id);

            if (existingGallery == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                galleryService.delete(existingGallery);
                return new ResponseEntity<>(Collections.singletonMap("message", "Gallery successfully deleted."), HttpStatus.OK);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
