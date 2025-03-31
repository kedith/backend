package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceImplTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Test
    void validatePassword() {
        assertTrue(userDetailsService.validatePassword("12345Aa!"));
        assertTrue(userDetailsService.validatePassword(" 123 45Aa! "));
        assertTrue(userDetailsService.validatePassword("12345678!Aa"));
        assertFalse(userDetailsService.validatePassword("        "));
        assertFalse(userDetailsService.validatePassword("1Aa@"));
        assertFalse(userDetailsService.validatePassword(null));
        assertFalse(userDetailsService.validatePassword("1234567Aa"));
        assertFalse(userDetailsService.validatePassword("1234567a-"));
        assertFalse(userDetailsService.validatePassword("1234567A-"));
    }

    @Test
    void validateMobile() {
        assertFalse(userDetailsService.validateMobile(null));
        assertTrue(userDetailsService.validateMobile("0040748326873"));
        assertTrue(userDetailsService.validateMobile("0748326873"));
        assertTrue(userDetailsService.validateMobile("+40748326873"));
        assertFalse(userDetailsService.validateMobile("01708"));
    }

    @Test
    void validateEmail() {
        assertFalse(userDetailsService.validateEmail(null));
        assertTrue(userDetailsService.validateEmail("donationmanager@yahoo.com"));
        assertFalse(userDetailsService.validateEmail("_@_"));
        assertFalse(userDetailsService.validateEmail("boss_tudor@"));
        assertFalse(userDetailsService.validateEmail("boss_tudor@ya"));
        assertTrue(userDetailsService.validateEmail("boss_tudor@yahoo.com"));
        assertFalse(userDetailsService.validateEmail("boss_tudor@yahoo."));
    }

    @Test
    void validateNames() {
        assertFalse(userDetailsService.validateNames(null));
        assertTrue(userDetailsService.validateNames("Radu"));
        assertFalse(userDetailsService.validateNames("123iwe"));
        assertFalse(userDetailsService.validateNames("ra du"));
        assertFalse(userDetailsService.validateNames("S"));
    }

    @Test
    void changePassword(){
        String password = "password";
        User user = new User("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", password);
        userDetailsService.changePassword(user,"newPassword");
        assertFalse(passwordEncoder.matches(password,user.getPassword()));
    }

    @Test
    void deleteUserById(){
        User userToDelete = new User("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", "password");
        userDetailsService.saveUser(userToDelete);
        User returnedUser = userDetailsService.getUserByUsername("bosstd");
        userDetailsService.deleteUserById(returnedUser.getId());

        User deletedUser = userDetailsService.getUserById(returnedUser.getId());
        assertNull(deletedUser);
    }

    @Test
    void updateUser(){
        User user = new User("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", "password");
        userDetailsService.saveUser(user);

        User userToUpdate = userDetailsService.getUserByUsername("bosstd");
        userToUpdate.setMobileNumber("0748326874");
        userDetailsService.updateUser(userToUpdate);

        User returnedUser = userDetailsService.getUserByUsername("bosstd");
        assertNotEquals(user.getMobileNumber(),returnedUser.getMobileNumber());

        userDetailsService.deleteUserById(returnedUser.getId());
    }

    @Test
    void getUserByUsername(){
        User user = new User("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", "password");
        userDetailsService.saveUser(user);

        User returnedUser = userDetailsService.getUserByUsername("bosstd");
        assertEquals(user.getUsername(),returnedUser.getUsername());
        assertEquals(user.getEmail(),returnedUser.getEmail());

        userDetailsService.deleteUserById(returnedUser.getId());
    }

    @Test
    void getUserById(){
        User user = new User("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", "password");
        userDetailsService.saveUser(user);

        User returnedUserThroughUsername = userDetailsService.getUserByUsername("bosstd");
        User returnedUserThroughId = userDetailsService.getUserById(returnedUserThroughUsername.getId());

        assertEquals(returnedUserThroughUsername.getId(),returnedUserThroughId.getId());
        assertEquals(returnedUserThroughUsername.getUsername(),returnedUserThroughId.getUsername());
        assertEquals(returnedUserThroughUsername.getEmail(),returnedUserThroughId.getEmail());

        userDetailsService.deleteUserById(returnedUserThroughId.getId());

    }

    @Test
    void findAll(){
        User user = new User("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", "password");
        userDetailsService.saveUser(user);

        List<User> users = userDetailsService.findAll();
        User userWithId = userDetailsService.getUserByUsername("bosstd");

        boolean userFound = false;
        for(User userInDB : users){
            if (Objects.equals(userInDB.getId(), userWithId.getId())) {
                userFound = true;
                break;
            }
        }

        assertTrue(userFound);

        userDetailsService.deleteUserById(userWithId.getId());
    }

}