package com.sanmateo.profile.services;

import com.sanmateo.profile.dto.news.CreateNewsDto;
import com.sanmateo.profile.dto.news.NewsDto;
import com.sanmateo.profile.dto.news.UpdateNewsDto;
import com.sanmateo.profile.enums.Status;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.models.News;
import com.sanmateo.profile.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Service
public class NewsService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private NewsRepository newsRepository;

    public News createNews(final AppUser appUser, final  CreateNewsDto createNewsDto) {
        final News news = new News();
        news.setTitle(createNewsDto.getTitle());
        news.setBody(createNewsDto.getBody());
        news.setImageUrl(createNewsDto.getImageUrl());
        news.setSourceUrl(createNewsDto.getSourceUrl());
        news.setReportedBy(appUser);
        news.setUpdatedBy(null);
        /** set default status to 'Active' */
        news.setStatus(Status.ACTIVE);
        return newsRepository.save(news);
    }

    public News updateNews(final AppUser appUser, final News existingNews, final  UpdateNewsDto updateNewsDto) {
        if (updateNewsDto.getTitle() != null) {
            existingNews.setTitle(updateNewsDto.getTitle());
        }

        if (updateNewsDto.getBody() != null) {
            existingNews.setBody(updateNewsDto.getBody());
        }

        if (updateNewsDto.getImageUrl() != null) {
            existingNews.setImageUrl(updateNewsDto.getImageUrl());
        }

        if (updateNewsDto.getSourceUrl() != null) {
            existingNews.setSourceUrl(updateNewsDto.getSourceUrl());
        }

        if (updateNewsDto.getStatus() != null) {
            existingNews.setStatus(updateNewsDto.getStatus());
        }

        existingNews.setUpdatedBy(appUser);
        return newsRepository.save(existingNews);
    }

    public Page<News> findByStatus(final Pageable pageable, final Status status) {
        return newsRepository.findByStatus(pageable, status);
    }

    public News findOne(final String id) {
        return newsRepository.findOne(id);
    }

    public void delete(final News news) {
        newsRepository.delete(news);
    }

    public NewsDto convert(final News news) {
        final NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setStatus(news.getStatus());
        newsDto.setCreatedAt(news.getCreatedAt());
        newsDto.setUpdatedAt(news.getUpdatedAt());
        newsDto.setActive(news.getActive());
        newsDto.setTitle(news.getTitle());
        newsDto.setBody(news.getBody());
        newsDto.setImageUrl(news.getImageUrl());
        newsDto.setSourceUrl(news.getSourceUrl());
        newsDto.setReportedBy(appUserService.simpleUser(news.getReportedBy()));

        if (news.getUpdatedBy() != null) {
            newsDto.setUpdatedBy(appUserService.simpleUser(news.getUpdatedBy()));
        } else {
            newsDto.setUpdatedBy(null);
        }
        return newsDto;
    }
}
