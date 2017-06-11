package com.sanmateo.profile.repositories;

import com.sanmateo.profile.enums.NewsStatus;
import com.sanmateo.profile.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/11/17.
 */
public interface NewsRepository extends CrudRepository<News, String> {

    Page<News> findByStatus(final Pageable pageable, final NewsStatus status);
}
