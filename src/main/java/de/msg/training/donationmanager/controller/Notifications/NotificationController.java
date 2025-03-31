package de.msg.training.donationmanager.controller.Notifications;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Notifications;
import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.service.AuthorizationServiceImpl;
import de.msg.training.donationmanager.service.NotificationService;
import de.msg.training.donationmanager.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    public AuthorizationServiceImpl authorizationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notifications>> findAll(@RequestHeader("Authorization") String jwt) throws BusinessException {
        User user = authorizationService.UserFromJwt(jwt);
        List<Notifications> notifications = notificationService.findAll();
        notifications.removeIf(i -> user != i.getActor() && user != i.getNotifier());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Notifications>> findAllforUSer(@PathVariable String username) {
        User user = userDetailsService.getUserByUsername(username);
        Set<Notifications> notificationsSet = new HashSet<>();
        notificationsSet.addAll(notificationService.loadByActor(user));
        notificationsSet.addAll(notificationService.loadByNotifier(user));
        List<Notifications> notifications = notificationsSet.stream().toList();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Notifications> addNotification(Notifications notifications) throws BusinessException {

        if (notifications != null)
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        else
            throw new BusinessException(BusinessExceptionCode.NOTIFICATION_INVALID);
    }

}
