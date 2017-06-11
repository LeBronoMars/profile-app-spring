package com.sanmateo.profile.repositories;

import com.sanmateo.profile.enums.Status;
import com.sanmateo.profile.models.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/11/17.
 */
public interface GalleryRepository extends CrudRepository<Gallery, String> {

    Page<Gallery> findByStatus(final Pageable pageable, final Status status);
}
