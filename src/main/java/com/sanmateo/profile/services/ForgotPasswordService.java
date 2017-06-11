package com.sanmateo.profile.services;

import com.google.common.collect.Lists;
import com.sanmateo.profile.exceptions.CustomException;
import com.sanmateo.profile.models.AppUser;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * Created by rsbulanon on 6/11/17.
 */
@Service
public class ForgotPasswordService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppUserService appUserService;

    @Inject
    private PasswordEncoder passwordEncoder;


    @Value("${spring.mail.username}")
    private String username;

    @Value("${app.name}")
    private String appName;

    public void generateNewPassword(final String recipientEmail) {
        final Email email;
        final Optional<AppUser> appUser = appUserService.findByEmail(recipientEmail);

        if (appUser.isPresent()) {
            try {
                final String newPassword = RandomStringUtils.randomAlphanumeric(12);
                email = DefaultEmail.builder()
                        .from(new InternetAddress(username, appName))
                        .to(Lists.newArrayList(new InternetAddress(recipientEmail, appUser.get().getFullName())))
                        .subject("Forgot Password Request")
                        .body("Your new password " + newPassword + ". Please be sure that you'll change your password immediately. \r\n\r\n")
                        .encoding("UTF-8").build();
                emailService.send(email);
                appUser.get().setPassword(passwordEncoder.encode(newPassword));
                appUserService.save(appUser.get());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            throw  new CustomException("Email not found.");
        }
    }
}
