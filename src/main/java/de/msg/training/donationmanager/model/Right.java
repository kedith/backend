package de.msg.training.donationmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name="rights")
public class Right {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERight name;


    public Right() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(ERight name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public ERight getName() {
        return name;
    }
}
