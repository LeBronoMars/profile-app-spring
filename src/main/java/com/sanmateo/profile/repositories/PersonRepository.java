package com.sanmateo.profile.repositories;

import com.sanmateo.profile.models.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/19/17.
 */
public interface PersonRepository extends CrudRepository<Person, String> {
}
