package com.mykhailotiutiun.userservice.service;

import com.mykhailotiutiun.userservice.model.EmailVerificationToken;
import com.mykhailotiutiun.userservice.model.User;
import com.mykhailotiutiun.userservice.repository.EmailVerificationTokenRepository;
import com.mykhailotiutiun.userservice.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public EmailVerificationService(EmailVerificationTokenRepository emailVerificationTokenRepository, UserRepository userRepository, JavaMailSender mailSender) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public void beginVerification(User user){
        String token = String.valueOf(UUID.randomUUID());

        emailVerificationTokenRepository.save(
                EmailVerificationToken.builder()
                        .token(token)
                        .user(user)
                        .expiryDate(LocalDate.now().plusDays(1))
                        .build()
        );

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setFrom("octo-umbrella-noreply@ukr.net");
        email.setSubject("OctoUmbrella Registration Confirmation");
        email.setText("Confirm: http://localhost:8080/auth/confirm-registration?token=" + token);
        mailSender.send(email);
    }

    public void verify(String token){
        EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByToken(token).orElseThrow(EntityNotFoundException::new);
        if(!emailVerificationToken.getExpiryDate().isAfter(LocalDate.now())){
            throw new RuntimeException("Token is expired");
        }

        emailVerificationToken.getUser().setEnabled(true);
        userRepository.save(emailVerificationToken.getUser());
    }
}
