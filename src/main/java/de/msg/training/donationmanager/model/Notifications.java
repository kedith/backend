package de.msg.training.donationmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    private User actor;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "notifier_id", referencedColumnName = "id")
    private User notifier;
    @Enumerated(EnumType.STRING)
    private ENotifications notificationType;

    private String extraInfo;

    private LocalDateTime date;

    public Notifications(User actor, User notifier, ENotifications notificationType, String extraInfo, LocalDateTime date) {
        this.actor = actor;
        this.notifier = notifier;
        this.notificationType = notificationType;
        this.extraInfo = extraInfo;
        this.date = date;
    }
}
