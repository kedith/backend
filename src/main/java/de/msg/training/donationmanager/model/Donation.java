package de.msg.training.donationmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor

@Data
@Entity
@Table(name = "donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Double amount;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(length = 3)
    private ECurrency currency;

    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    @NonNull
    private Campaign campaign;

    @NonNull
    private String createdBy;

    @NonNull
    private LocalDate createdDate;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "donor_id", referencedColumnName = "id")
    private Donor benefactor;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean approved;

    private String approvedBy;

    private LocalDate approvedDate;

    private String notes;

    @Version
    private Long version;

}
