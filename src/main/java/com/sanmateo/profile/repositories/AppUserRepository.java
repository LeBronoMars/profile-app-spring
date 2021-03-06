package com.sanmateo.profile.repositories;

import com.sanmateo.profile.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by rsbulanon on 6/11/17.
 */
public interface AppUserRepository extends CrudRepository<AppUser, String> {

    Page<AppUser> findAll(Pageable pageable);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByUsernameAndIdNot(String username, String id);

    Optional<AppUser> findByEmailAndIdNot(String email, String id);
}
