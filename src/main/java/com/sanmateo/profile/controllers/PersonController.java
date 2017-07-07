package com.sanmateo.profile.controllers;


import com.sanmateo.profile.dto.person.CreatePersonDto;
import com.sanmateo.profile.exceptions.CustomException;
import com.sanmateo.profile.models.Person;
import com.sanmateo.profile.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Controller
@RequestMapping(path = "/api")
@Api(value = "User Module")
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    /**
     * create new person
     */
    @RequestMapping(value = "/person",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody CreatePersonDto createPersonDto) throws URISyntaxException {
        try {
            final Person person = personService.createPerson(createPersonDto);
            return new ResponseEntity<>(Collections.singletonMap("message", "New record created."), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * get all users
     */
    @RequestMapping(value = "/people",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<?> getPeople() throws URISyntaxException {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

}
