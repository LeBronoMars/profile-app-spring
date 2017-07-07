package com.sanmateo.profile.services;


import com.sanmateo.profile.dto.person.CreatePersonDto;
import com.sanmateo.profile.models.Person;
import com.sanmateo.profile.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Service
@Transactional
public class PersonService {
    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(final CreatePersonDto createPersonDto) {
        final Person person = new Person();
        person.setId(null);
        person.setFirstName(createPersonDto.getFirstName());
        person.setLastName(createPersonDto.getLastName());
        person.setBirthDay(createPersonDto.getBirthDay());
        person.setEmail(createPersonDto.getEmail());
        person.setMobileNumber(createPersonDto.getMobileNumber());
        personRepository.save(person);
        return person;
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }
}
