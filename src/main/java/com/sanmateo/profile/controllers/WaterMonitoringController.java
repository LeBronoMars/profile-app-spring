package com.sanmateo.profile.controllers;


import com.sanmateo.profile.dto.waterlevel.CreateWaterMonitoringDto;
import com.sanmateo.profile.dto.waterlevel.WaterMonitoringDto;
import com.sanmateo.profile.enums.UserRole;
import com.sanmateo.profile.exceptions.CustomException;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.models.WaterMonitoring;
import com.sanmateo.profile.services.AppUserService;
import com.sanmateo.profile.services.WaterMonitoringService;
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
@Api(value = "Water Monitoring Module")
public class WaterMonitoringController {

    private final Logger log = LoggerFactory.getLogger(WaterMonitoringController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private WaterMonitoringService waterMonitoringService;

    /**
     * create water level record
     */
    @RequestMapping(value = "/water_monitoring",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createWaterMonitoring(@Valid @RequestBody CreateWaterMonitoringDto createWaterMonitoringDto, HttpServletRequest request) {
        log.info("REST request to create water level : {}", createWaterMonitoringDto);
        return Optional.ofNullable(request.getRemoteUser()).map(user -> {
            final AppUser appUser = appUserService.findByUsername(user);
            try {
                if (appUser.getRole().equals(UserRole.REGULAR_USER)) {
                    return new ResponseEntity<>(Collections.singletonMap("message", "You are not allowed to access this resource."), HttpStatus.BAD_REQUEST);
                } else {
                    final WaterMonitoring waterMonitoring = waterMonitoringService.createWaterLevelRecord(appUser, createWaterMonitoringDto);
                    final WaterMonitoringDto waterMonitoringDto = waterMonitoringService.convert(waterMonitoring);
                    return ResponseEntity.created(new URI("/api/water_monitoring/" + waterMonitoring.getId())).body(waterMonitoringDto);
                }
            } catch (CustomException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            } catch (URISyntaxException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            }
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    /**
     * get all water level records
     */
    @RequestMapping(value = "/water_monitoring",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<Page<WaterMonitoringDto>> getWaterMonitorings(Pageable pageable, @RequestParam(name = "area", required = false) String area) throws URISyntaxException {
        Page<WaterMonitoringDto> waterLevelDtos = null;
        if (area != null && !area.isEmpty()) {
            waterLevelDtos = waterMonitoringService.findByArea(pageable, area).map(source -> waterMonitoringService.convert(source));
        } else {
            waterLevelDtos = waterMonitoringService.findAll(pageable).map(source -> waterMonitoringService.convert(source));
        }
        return new ResponseEntity<>(waterLevelDtos, HttpStatus.OK);
    }

    /**
     * get water level by id
     * */
    @RequestMapping(value = "/water_monitoring/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWaterMonitoringRecordById(@PathVariable String id) {
        try {
            final WaterMonitoring waterMonitoring = waterMonitoringService.findOne(id);

            if (waterMonitoring == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                final WaterMonitoringDto waterMonitoringDto = waterMonitoringService.convert(waterMonitoring);
                return ResponseEntity.ok().body(waterMonitoringDto);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete water level record
     * */
    @RequestMapping(value = "/water_monitoring/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteWaterMonitoringRecord(@PathVariable String id) {
        try {
            final WaterMonitoring waterMonitoring = waterMonitoringService.findOne(id);

            if (waterMonitoring == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                waterMonitoringService.delete(waterMonitoring);
                return new ResponseEntity<>(Collections.singletonMap("message", "Water monitoring record successfully deleted."), HttpStatus.OK);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
