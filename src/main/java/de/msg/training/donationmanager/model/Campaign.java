package de.msg.training.donationmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "campaigns")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private String purpose;
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Donation> donations = new ArrayList<>();

    @Version
    private Long version;
}
