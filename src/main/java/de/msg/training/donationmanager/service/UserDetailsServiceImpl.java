package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;


    @Autowired
    private EmailService emailService;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id)  {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public User saveUser(User user) {
        user.setActive(true);
        user.setFirstLogin(true);
        user.setPassword(String.valueOf(UUID.randomUUID()));
        user.setVersion(0L);
        emailService.simpleMessageSender(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void changePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

    }

    public boolean validatePassword(String password) {
        if (password == null)
            return false;
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_+.]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public boolean validateMobile(String mobile) {
        if (mobile == null)
            return false;
        Pattern pattern = Pattern.compile("^(00407|07|\\+407)\\d{8}$");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.find();
    }

    public boolean validateEmail(String mail) {
        if (mail == null)
            return false;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*[-_]?[a-zA-Z0-9]+(?:[-_][a-zA-Z0-9]+)*@([a-zA-Z0-9]+(?:[-][a-zA-Z0-9]+)*\\.)+[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(mail);
        return matcher.find();
    }

    public boolean validateNames(String name) {
        if (name == null)
            return false;
        Pattern pattern = Pattern.compile("^[A-Z][a-z]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
