package de.msg.training.donationmanager.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto {
    private Long id;
    private String name;
    private String purpose;
    private Long version;

    public CampaignDto(Long id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }
}
