package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.model.Notifications;
import de.msg.training.donationmanager.model.User;
import de.msg.training.donationmanager.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository){this.notificationRepository = notificationRepository;}

    public List<Notifications> findAll(){
        return notificationRepository.findAll();
    }
    public List<Notifications> loadByActor(User user){
        return notificationRepository.findAllByActor(user);
    }
    public List<Notifications> loadByNotifier(User user){
        return notificationRepository.findByAllNotifier(user);
    }
    public void saveNotification(Notifications notification){
        notificationRepository.save(notification);
    }


}
