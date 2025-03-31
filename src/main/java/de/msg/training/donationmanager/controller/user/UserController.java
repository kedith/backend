package de.msg.training.donationmanager.controller.user;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.ENotifications;
import de.msg.training.donationmanager.model.Notifications;
import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.model.dtos.UserDto;
import de.msg.training.donationmanager.model.mapper.UserMapper;
import de.msg.training.donationmanager.net.websocket.service.WebsocketService;
import de.msg.training.donationmanager.service.AuthorizationServiceImpl;
import de.msg.training.donationmanager.service.NotificationService;
import de.msg.training.donationmanager.service.UserDetailsServiceImpl;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static de.msg.training.donationmanager.model.ERight.USER_MANAGEMENT;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserMapper userMapper;


    @Autowired
    public NotificationService notificationService;
    @Autowired
    public AuthorizationServiceImpl authorizationService;
    @Autowired
    private WebsocketService websocketService;

    @Autowired
    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(@RequestHeader("Authorization") String jwtToken) throws BusinessException {

        authorizationService.checkUserAuthorization(jwtToken, USER_MANAGEMENT);
        List<UserDto> users = userMapper.dtosFromUsers(userService.findAll());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken, USER_MANAGEMENT);
        if (userService.getUserById(id) == null) {
            throw new BusinessException(BusinessExceptionCode.USER_NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.userDtoFromUser(userService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<UserDto> getUserByUsername(@RequestBody String username, @RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken, USER_MANAGEMENT);
        if (userService.getUserByUsername(username) == null) {
            throw new BusinessException(BusinessExceptionCode.USER_NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.userDtoFromUser(userService.getUserByUsername(username)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken, USER_MANAGEMENT);
        if (!userService.validateEmail(userDto.getEmail()) || !userService.validateMobile(userDto.getMobileNumber())
                || !userService.validateNames(userDto.getFirstName()) || !userService.validateNames(userDto.getLastName())) {
            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
        } else {
            UserDto createdUser = userMapper.userDtoFromUser(userService.saveUser(userMapper.userFromUserDto(userDto)));
            User user = userMapper.userFromUserDto(userDto);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String jwtToken) throws Exception {
        authorizationService.checkUserAuthorization(jwtToken, USER_MANAGEMENT);
        User user = authorizationService.UserFromJwt(jwtToken);
        User oldUser = userService.getUserById(userDto.getId());

        if (oldUser == null)
            throw new BusinessException(BusinessExceptionCode.USER_NOT_FOUND);
        try {

            if (!Objects.equals(oldUser.getVersion(), userDto.getVersion())) {
                throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
            }

            if (oldUser.isActive() && !userDto.isActive()) {

                notificationService.saveNotification(new Notifications(user, user, ENotifications.USER_DELETED, "", LocalDateTime.now()));
                websocketService.sendMessagesForUserDeleted(userDto.getUsername());
            } else {

                notificationService.saveNotification(new Notifications(user, userMapper.userFromUserDto(userDto), ENotifications.USER_UPDATED, "", LocalDateTime.now()));
                websocketService.sendMessagesForUserUpdated(userDto.getUsername());
            }

            User updatedUser = userMapper.userFromUserDto(userDto);
            User returnedUpdatedUser = userService.updateUser(updatedUser);
            UserDto returnedUserDto = userMapper.userDtoFromUser(returnedUpdatedUser);

            return new ResponseEntity<>(returnedUserDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionCode.NOTIFICATION_ERROR);
        }
    }

    @PutMapping("/update-password/{username}")
    public ResponseEntity<String> updatePassword(@PathVariable String username, @RequestBody String newPassword) throws BusinessException {
        boolean validPassword = userService.validatePassword(newPassword);

        if (!validPassword)
            throw new BusinessException(BusinessExceptionCode.INVALID_PASSWORD);
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new BusinessException(BusinessExceptionCode.USER_NOT_FOUND);
        } else {
            try {
                userService.changePassword(user, newPassword);
                user.setFirstLogin(false);
                userService.updateUser(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (OptimisticLockException ex) {
                throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken, USER_MANAGEMENT);
        if (userService.getUserById(id) != null) {
            try {
                userService.deleteUserById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (OptimisticLockException ex) {
                throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
            }
        } else {
            throw new BusinessException(BusinessExceptionCode.USER_NOT_FOUND);
        }
    }
}
