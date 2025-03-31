package de.msg.training.donationmanager.model.dtos;

import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.Donor;
import de.msg.training.donationmanager.model.ECurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationDto {
    private Long id;
    private Double amount;
    private ECurrency currency;
    private Campaign campaign;
    private String createdBy;
    private LocalDate createdDate;
    private Donor benefactor;
    private boolean approved;
    private String approvedBy;
    private LocalDate approvedDate;
    private String notes;
    private Long version;
}
