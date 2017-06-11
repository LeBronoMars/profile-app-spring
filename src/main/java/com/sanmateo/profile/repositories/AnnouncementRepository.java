package com.sanmateo.profile.repositories;

import com.sanmateo.profile.models.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/11/17.
 */
public interface AnnouncementRepository extends CrudRepository<Announcement, String> {

    Page<Announcement> findAll(final Pageable pageable);
}
