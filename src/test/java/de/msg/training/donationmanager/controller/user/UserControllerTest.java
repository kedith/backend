package de.msg.training.donationmanager.controller.user;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.model.dtos.UserDto;
import de.msg.training.donationmanager.model.mapper.UserMapper;
import de.msg.training.donationmanager.service.AuthorizationServiceImpl;
import de.msg.training.donationmanager.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;
    @Mock
    UserDetailsServiceImpl userService;
    @Autowired
    UserMapper userMapper;

    @Mock
    AuthorizationServiceImpl authorizationService;
    public final String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbkEiLCJyb2xlcyI6WyJBRE0iXSwiaWF0IjoxNjkzMjAxNzcxLCJleHAiOjE2OTMyMzE3NzF9.faI2XEteldqCwmrdu-B0_0VBQ9L0eDFb5qapRqidMwwvVanp5UIIetaeTJktHUZZEme1CApPXPL465k5fvHZXQ";
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void deleteUser_Missing() {
        BusinessException exception = assertThrows(BusinessException.class, () -> userController.deleteUser(0L,token));
        assertEquals(BusinessExceptionCode.USER_NOT_FOUND, exception.getBusinessExceptionCode());
    }

    @Test
    void updateUser_Missing(){
        UserDto userDTO = new UserDto();
        userDTO.setId(0L);
        BusinessException exception = assertThrows(BusinessException.class, () -> userController.updateUser(userDTO, token));
        assertEquals(BusinessExceptionCode.USER_NOT_FOUND, exception.getBusinessExceptionCode());
    }

    @Test
    void getUserByUsername() throws BusinessException {
        UserDto userDto = new UserDto("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", "password");
        ResponseEntity<UserDto> createEntityValid = userController.createUser(userDto,token);

        ResponseEntity<UserDto> getUserNameEntity = userController.getUserByUsername(Objects.requireNonNull(createEntityValid.getBody()).getUsername(),token);
        assertNotNull(getUserNameEntity);
        assertEquals(HttpStatus.OK, getUserNameEntity.getStatusCode());
        assertEquals(userDto.getUsername(), Objects.requireNonNull(getUserNameEntity.getBody()).getUsername());

        userController.deleteUser(Objects.requireNonNull(getUserNameEntity.getBody()).getId(),token);
    }

    @Test
    void getUserByUsername_Missing() {
        BusinessException exception = assertThrows(BusinessException.class, () -> userController.getUserByUsername("B",token));
        assertEquals(BusinessExceptionCode.USER_NOT_FOUND, exception.getBusinessExceptionCode());
    }



    @Test
    void getUserById_Missing() throws BusinessException {
        doThrow(new BusinessException(BusinessExceptionCode.USER_NOT_AUTHORIZED))
                .when(authorizationService).checkUserAuthorization(anyString(), any());

        when(userService.getUserById(0L)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> userController.getUserById(0L, token));
        assertEquals(BusinessExceptionCode.USER_NOT_FOUND, exception.getBusinessExceptionCode());

    }

    @Test
    void findAll() throws Exception {
        UserDto userDto = new UserDto("Tudor", "Boss", "+40748326874", "bosstd", "john@example.com", "password");
        ResponseEntity<UserDto> createEntityValid = userController.createUser(userDto,token);

        ResponseEntity<List<UserDto>> findAllResponseEntity = userController.findAll(token);
        assertNotNull(findAllResponseEntity);
        assertEquals(HttpStatus.OK, findAllResponseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(findAllResponseEntity.getBody()).contains(createEntityValid.getBody()));

        userController.deleteUser(Objects.requireNonNull(createEntityValid.getBody()).getId(),token);
    }

}