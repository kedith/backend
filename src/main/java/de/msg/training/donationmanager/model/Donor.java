package de.msg.training.donationmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "donor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String additionalName;

    private String maidenName;
    @OneToMany(mappedBy = "benefactor")
    @JsonIgnore
    private List<Donation> donations = new ArrayList<>();

    @Version
    private Long version;

    public Donor(Long id, @NonNull String firstName, @NonNull String lastName, String additionalName, String maidenName, List<Donation> donations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.additionalName = additionalName;
        this.maidenName = maidenName;
        this.donations = donations;
    }
}
