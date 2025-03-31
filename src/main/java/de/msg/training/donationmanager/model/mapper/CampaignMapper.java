package de.msg.training.donationmanager.model.mapper;

import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.dtos.CampaignDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CampaignMapper {
    public abstract CampaignDto campaignDtoFromCampaign(Campaign campaign);

    public abstract Campaign campaignFromCampaignDto(CampaignDto campaignDto);
}
