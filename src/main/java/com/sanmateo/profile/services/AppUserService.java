package com.sanmateo.profile.services;


import com.sanmateo.profile.dto.user.AppUserDto;
import com.sanmateo.profile.dto.user.AppUserRegistrationDto;
import com.sanmateo.profile.dto.user.SimplifiedAppUserDto;
import com.sanmateo.profile.dto.user.UpdateUserDto;
import com.sanmateo.profile.exceptions.CustomException;
import com.sanmateo.profile.exceptions.NotFoundException;
import com.sanmateo.profile.models.AppUser;
import com.sanmateo.profile.repositories.AppUserRepository;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Service
@Transactional
public class AppUserService {
    private final Logger log = LoggerFactory.getLogger(AppUserService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser createUser(final AppUserRegistrationDto appUserRegistrationDto) {
        final Optional<AppUser> existingUserByUsername = appUserRepository.findByUsername(appUserRegistrationDto.getUsername());

        if (existingUserByUsername.isPresent()) {
            throw new CustomException("Username: '" + appUserRegistrationDto.getUsername() + "' already in use.");
        } else {
            final Optional<AppUser> existingUserByEmail = appUserRepository.findByEmail(appUserRegistrationDto.getEmail());

            if (existingUserByEmail.isPresent()) {
                throw new CustomException("Email: '" + appUserRegistrationDto.getEmail() + "' already in use.");
            } else {
                final AppUser newUser = new AppUser();
                newUser.setId(null);
                newUser.setFirstName(appUserRegistrationDto.getFirstName());
                newUser.setMiddleName(appUserRegistrationDto.getMiddleName());
                newUser.setLastName(appUserRegistrationDto.getLastName());
                newUser.setAddress(appUserRegistrationDto.getAddress());
                newUser.setContactNo(appUserRegistrationDto.getContactNo());
                newUser.setEmail(appUserRegistrationDto.getEmail());
                newUser.setUsername(appUserRegistrationDto.getUsername());
                newUser.setRole(appUserRegistrationDto.getRole());
                newUser.setGender(appUserRegistrationDto.getGender());
                newUser.setPassword(passwordEncoder.encode(appUserRegistrationDto.getPassword()));

                /** set default status to 'Active' */
                newUser.setStatus("Active");

                /** generate default Avatar */
                final String encodedUsername = passwordEncoder.encode(appUserRegistrationDto.getEmail());
                newUser.setPicUrl("http://www.gravatar.com/avatar/" + encodedUsername + "?d=identicon");

                appUserRepository.save(newUser);
                log.info("New user successfully created: {}", newUser);
                return newUser;
            }
        }
    }

    public AppUser updateUser(final AppUser existingUser, final UpdateUserDto updateUserDto) {
        final Optional<AppUser> existingUserByUsername = appUserRepository.findByUsernameAndIdNot(updateUserDto.getUsername(), existingUser.getId());

        if (existingUserByUsername.isPresent()) {
            throw new CustomException("Username: '" + updateUserDto.getUsername() + "' already in use.");
        } else {
            final Optional<AppUser> existingUserByEmail = appUserRepository.findByEmailAndIdNot(updateUserDto.getEmail(), existingUser.getId());

            if (existingUserByEmail.isPresent()) {
                throw new CustomException("Email: '" + updateUserDto.getEmail() + "' already in use.");
            } else {
                existingUser.setFirstName(updateUserDto.getFirstName());
                existingUser.setMiddleName(updateUserDto.getMiddleName());
                existingUser.setLastName(updateUserDto.getLastName());
                existingUser.setAddress(updateUserDto.getAddress());
                existingUser.setContactNo(updateUserDto.getContactNo());
                existingUser.setEmail(updateUserDto.getEmail());
                existingUser.setUsername(updateUserDto.getUsername());
                existingUser.setRole(updateUserDto.getRole());
                existingUser.setGender(updateUserDto.getGender());
                existingUser.setPicUrl(updateUserDto.getPicUrl());
                appUserRepository.save(existingUser);
                return existingUser;
            }
        }
    }

    public Page<AppUser> findAll(Pageable pageable) {
        return appUserRepository.findAll(pageable);
    }

    public AppUser findOne(final String id) {
        return appUserRepository.findOne(id);
    }

    public void save(final AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public Optional<AppUser> findByEmail(final String email) {
        return appUserRepository.findByEmail(email);
    }

    public AppUser findByUsername(String username) {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        return appUser.map(user -> user).orElseThrow(() -> new NotFoundException(AppUser.class, username));
    }

    public AppUserDto convert(final AppUser appUser) {
        final AppUserDto userDto = new AppUserDto();
        userDto.setId(appUser.getId());
        userDto.setCreatedAt(appUser.getCreatedAt());
        userDto.setUpdatedAt(appUser.getUpdatedAt());
        userDto.setActive(appUser.getActive());
        userDto.setFirstName(appUser.getFirstName());
        userDto.setMiddleName(appUser.getMiddleName());
        userDto.setLastName(appUser.getLastName());
        userDto.setAddress(appUser.getAddress());
        userDto.setContactNo(appUser.getContactNo());
        userDto.setEmail(appUser.getEmail());
        userDto.setUsername(appUser.getUsername());
        userDto.setRole(appUser.getRole());
        userDto.setStatus(appUser.getStatus());
        userDto.setPicUrl(appUser.getPicUrl());
        userDto.setGender(appUser.getGender());
        userDto.setSynced(appUser.isSynced());
        return userDto;
    }

    public SimplifiedAppUserDto simpleUser(final AppUser appUser) {
        final SimplifiedAppUserDto userDto = new SimplifiedAppUserDto();
        userDto.setId(appUser.getId());
        userDto.setFirstName(appUser.getFirstName());
        userDto.setMiddleName(appUser.getMiddleName());
        userDto.setLastName(appUser.getLastName());
        userDto.setContactNo(appUser.getContactNo());
        userDto.setEmail(appUser.getEmail());
        userDto.setPicUrl(appUser.getPicUrl());
        return userDto;
    }

}
