package com.sanmateo.profile.controllers;


import com.sanmateo.profile.dto.news.CreateNewsDto;
import com.sanmateo.profile.dto.news.NewsDto;
import com.sanmateo.profile.dto.news.UpdateNewsDto;
import com.sanmateo.profile.enums.NewsStatus;
import com.sanmateo.profile.exceptions.CustomException;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.models.News;
import com.sanmateo.profile.services.AppUserService;
import com.sanmateo.profile.services.NewsService;
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
@Api(value = "News Module")
public class NewsController {

    private final Logger log = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private NewsService newsService;

    /**
     * create news
     */
    @RequestMapping(value = "/news",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNews(@Valid @RequestBody CreateNewsDto createNewsDto, HttpServletRequest request) {
        log.info("REST request to create News : {}", createNewsDto);
        return Optional.ofNullable(request.getRemoteUser()).map(user -> {
            final AppUser appUser = appUserService.findByUsername(user);
            try {
                final News news = newsService.createNews(appUser, createNewsDto);
                final NewsDto newsDto = newsService.convert(news);
                return ResponseEntity.created(new URI("/api/news/" + news.getId())).body(newsDto);
            } catch (CustomException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            } catch (URISyntaxException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            }
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    /**
     * update news
     */
    @RequestMapping(value = "/news/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateNews(@PathVariable String id,
                                        @Valid @RequestBody UpdateNewsDto updateNewsDto, HttpServletRequest request) {
        log.info("REST request to update News : {}", updateNewsDto);
        return Optional.ofNullable(request.getRemoteUser()).map(user -> {
            final AppUser appUser = appUserService.findByUsername(user);
            try {
                final News existingNews = newsService.findOne(id);

                if (existingNews == null) {
                    return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
                } else {
                    final News news = newsService.updateNews(appUser, existingNews, updateNewsDto);
                    final NewsDto newsDto = newsService.convert(news);
                    return ResponseEntity.ok().body(newsDto);
                }
            } catch (CustomException e) {
                return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
            }
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

    /**
     * get all news
     */
    @RequestMapping(value = "/news",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<Page<NewsDto>> getNews(Pageable pageable, @RequestParam("status") NewsStatus status) throws URISyntaxException {
        final Page<NewsDto> newsDtos = newsService.findByStatus(pageable, status).map(source -> newsService.convert(source));
        return new ResponseEntity<>(newsDtos, HttpStatus.OK);
    }

    /**
     * get news by id
     * */
    @RequestMapping(value = "/news/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNewsById(@PathVariable String id) {
        try {
            final News existingNews = newsService.findOne(id);

            if (existingNews == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                final NewsDto newsDto = newsService.convert(existingNews);
                return ResponseEntity.ok().body(newsDto);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * delete news
     * */
    @RequestMapping(value = "/news/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNews(@PathVariable String id) {
        try {
            final News existingNews = newsService.findOne(id);

            if (existingNews == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Record not found."), HttpStatus.NOT_FOUND);
            } else {
                newsService.delete(existingNews);
                return new ResponseEntity<>(Collections.singletonMap("message", "News successfully deleted."), HttpStatus.OK);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
