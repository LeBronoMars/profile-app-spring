package com.sanmateo.profile.repositories;

import com.sanmateo.profile.models.WaterMonitoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/11/17.
 */
public interface WaterMonitoringRepository extends CrudRepository<WaterMonitoring, String> {

    Page<WaterMonitoring> findAll(final Pageable pageable);

    Page<WaterMonitoring> findByArea(final Pageable pageable, final String area);
}
