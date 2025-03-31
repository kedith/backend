package de.msg.training.donationmanager.controller.auth;

import static org.mockito.Mockito.*;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;


    @Test
    public void testUpdateLoginAttemptsSuccess() throws BusinessException {
        User user = new User();
        user.setAttempts((short) 3);

        authController.updateLoginAttempts(user, true);

        verify(userDetailsService).updateUser(argThat(savedUser -> savedUser.getAttempts() == 0));
    }

    @Test
    public void testUpdateLoginAttemptsFailure() throws BusinessException {
        User user = new User();
        user.setAttempts((short) 3);

        authController.updateLoginAttempts(user, false);

        verify(userDetailsService).updateUser(argThat(savedUser -> savedUser.getAttempts() == 4));
    }
}
