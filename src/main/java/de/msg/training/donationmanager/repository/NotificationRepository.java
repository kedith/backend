package de.msg.training.donationmanager.repository;

import de.msg.training.donationmanager.model.Notifications;
import de.msg.training.donationmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {
    @Modifying
    @Query(value="select n from Notifications n order by n.date desc")
    List<Notifications>findAll();
    Optional<Notifications> findById(Long id);
    @Modifying
    @Query(value="select n from Notifications n order by n.date desc")
    List<Notifications> findAllByActor(User actor);
    @Modifying
    @Query(value="select n from Notifications n order by n.date desc")
    List<Notifications> findByAllNotifier(User user);

}
