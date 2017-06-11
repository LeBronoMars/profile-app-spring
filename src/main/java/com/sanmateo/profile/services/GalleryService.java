package com.sanmateo.profile.services;

import com.sanmateo.profile.dto.gallery.CreateGalleryDto;
import com.sanmateo.profile.dto.gallery.GalleryDto;
import com.sanmateo.profile.dto.gallery.UpdateGalleryDto;
import com.sanmateo.profile.enums.Status;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.models.Gallery;
import com.sanmateo.profile.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Service
public class GalleryService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private GalleryRepository galleryRepository;

    public Gallery createGallery(final AppUser appUser, final CreateGalleryDto createGalleryDto) {
        final Gallery gallery = new Gallery();
        gallery.setTitle(createGalleryDto.getTitle());
        gallery.setDescription(createGalleryDto.getDescription());
        gallery.setImageUrl(createGalleryDto.getImageUrl());
        gallery.setCreatedBy(appUser);
        gallery.setUpdatedBy(null);
        /** set default status to 'Active' */
        gallery.setStatus(Status.ACTIVE);
        return galleryRepository.save(gallery);
    }

    public Gallery updateGallery(final AppUser appUser, final Gallery existingGallery, final UpdateGalleryDto updateGalleryDto) {
        if (updateGalleryDto.getTitle() != null) {
            existingGallery.setTitle(updateGalleryDto.getTitle());
        }

        if (updateGalleryDto.getDescription() != null) {
            existingGallery.setDescription(updateGalleryDto.getDescription());
        }

        if (updateGalleryDto.getImageUrl() != null) {
            existingGallery.setImageUrl(updateGalleryDto.getImageUrl());
        }

        if (updateGalleryDto.getStatus() != null) {
            existingGallery.setStatus(updateGalleryDto.getStatus());
        }

        existingGallery.setUpdatedBy(appUser);
        return galleryRepository.save(existingGallery);
    }

    public Page<Gallery> findByStatus(final Pageable pageable, final Status status) {
        return galleryRepository.findByStatus(pageable, status);
    }

    public Gallery findOne(final String id) {
        return galleryRepository.findOne(id);
    }

    public void delete(final Gallery gallery) {
        galleryRepository.delete(gallery);
    }

    public GalleryDto convert(final Gallery gallery) {
        final GalleryDto galleryDto = new GalleryDto();
        galleryDto.setId(gallery.getId());
        galleryDto.setStatus(gallery.getStatus());
        galleryDto.setCreatedAt(gallery.getCreatedAt());
        galleryDto.setUpdatedAt(gallery.getUpdatedAt());
        galleryDto.setActive(gallery.getActive());
        galleryDto.setTitle(gallery.getTitle());
        galleryDto.setDescription(gallery.getDescription());
        galleryDto.setImageUrl(gallery.getImageUrl());
        galleryDto.setCreatedBy(appUserService.simpleUser(gallery.getCreatedBy()));

        if (gallery.getUpdatedBy() != null) {
            galleryDto.setUpdatedBy(appUserService.simpleUser(gallery.getUpdatedBy()));
        } else {
            galleryDto.setUpdatedBy(null);
        }
        return galleryDto;
    }
}
