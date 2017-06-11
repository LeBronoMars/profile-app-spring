package com.sanmateo.profile.services;

import com.sanmateo.profile.dto.waterlevel.CreateWaterMonitoringDto;
import com.sanmateo.profile.dto.waterlevel.WaterMonitoringDto;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.models.WaterMonitoring;
import com.sanmateo.profile.repositories.WaterMonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Service
public class WaterMonitoringService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private WaterMonitoringRepository waterMonitoringRepository;

    public WaterMonitoring createWaterLevelRecord(final AppUser appUser, final CreateWaterMonitoringDto createWaterMonitoringDto) {
        final WaterMonitoring waterMonitoring = new WaterMonitoring();
        waterMonitoring.setArea(createWaterMonitoringDto.getArea());
        waterMonitoring.setLevel(createWaterMonitoringDto.getLevel());

        if (createWaterMonitoringDto.getLevel() <= 18) {
            waterMonitoring.setAlert("Alert");
        } else if (createWaterMonitoringDto.getLevel() >= 18.01 && createWaterMonitoringDto.getLevel() <= 19.00) {
            waterMonitoring.setAlert("Alarm");
        } else if (createWaterMonitoringDto.getLevel() >= 19.01) {
            waterMonitoring.setAlert("Critical");
        }

        waterMonitoring.setCreatedBy(appUser);
        return waterMonitoringRepository.save(waterMonitoring);
    }

    public Page<WaterMonitoring> findAll(final Pageable pageable) {
        return waterMonitoringRepository.findAll(pageable);
    }

    public Page<WaterMonitoring> findByArea(final Pageable pageable, final String area) {
        return waterMonitoringRepository.findByArea(pageable, area);
    }

    public WaterMonitoring findOne(final String id) {
        return waterMonitoringRepository.findOne(id);
    }

    public void delete(final WaterMonitoring WaterMonitoring) {
        waterMonitoringRepository.delete(WaterMonitoring);
    }

    public WaterMonitoringDto convert(final WaterMonitoring waterMonitoring) {
        final WaterMonitoringDto waterMonitoringDto = new WaterMonitoringDto();
        waterMonitoringDto.setId(waterMonitoring.getId());
        waterMonitoringDto.setCreatedAt(waterMonitoring.getCreatedAt());
        waterMonitoringDto.setUpdatedAt(waterMonitoring.getUpdatedAt());
        waterMonitoringDto.setArea(waterMonitoring.getArea());
        waterMonitoringDto.setLevel(waterMonitoring.getLevel());
        waterMonitoringDto.setAlert(waterMonitoring.getAlert());
        waterMonitoringDto.setActive(waterMonitoring.getActive());
        waterMonitoringDto.setCreatedBy(appUserService.simpleUser(waterMonitoring.getCreatedBy()));
        return waterMonitoringDto;
    }
}
