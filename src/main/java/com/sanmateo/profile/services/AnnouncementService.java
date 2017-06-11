package com.sanmateo.profile.services;

import com.sanmateo.profile.dto.announcement.AnnouncementDto;
import com.sanmateo.profile.dto.announcement.CreateAnnouncementDto;
import com.sanmateo.profile.models.Announcement;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Service
public class AnnouncementService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement createAnnouncement(final AppUser appUser, final CreateAnnouncementDto createAnnouncementDto) {
        final Announcement announcement = new Announcement();
        announcement.setTitle(createAnnouncementDto.getTitle());
        announcement.setMessage(createAnnouncementDto.getMessage());
        announcement.setCreatedBy(appUser);
        return announcementRepository.save(announcement);
    }

    public Page<Announcement> findAll(final Pageable pageable) {
        return announcementRepository.findAll(pageable);
    }

    public Announcement findOne(final String id) {
        return announcementRepository.findOne(id);
    }

    public void delete(final Announcement announcement) {
        announcementRepository.delete(announcement);
    }

    public AnnouncementDto convert(final Announcement announcement) {
        final AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setId(announcement.getId());
        announcementDto.setCreatedAt(announcement.getCreatedAt());
        announcementDto.setUpdatedAt(announcement.getUpdatedAt());
        announcementDto.setTitle(announcement.getTitle());
        announcementDto.setMessage(announcement.getMessage());
        announcementDto.setActive(announcement.getActive());
        announcementDto.setCreatedBy(appUserService.simpleUser(announcement.getCreatedBy()));
        return announcementDto;
    }
}
