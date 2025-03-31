package de.msg.training.donationmanager.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String additionalName;
    private String maidenName;
    private Long version;
}
