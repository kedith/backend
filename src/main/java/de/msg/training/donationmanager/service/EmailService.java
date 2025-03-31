package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void simpleMessageSender(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("donationmanager7@gmail.com");

        String subject = "Welcome to DonationManager";
        String message = "Hello, " + user.getFirstName() + '\n' + '\n' + "Your credentials are: " + '\n' + "Username: " + user.getUsername()
                + '\n' + "Password: " + user.getPassword() + '\n' + '\n' + "You will need to change this password when you first login";

        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }
}
