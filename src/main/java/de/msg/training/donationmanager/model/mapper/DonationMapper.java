package de.msg.training.donationmanager.model.mapper;

import de.msg.training.donationmanager.model.Donation;
import de.msg.training.donationmanager.model.dtos.DonationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class DonationMapper {
    public abstract DonationDto donationDtoFromDonation(Donation donation);
    public abstract Donation donationFromDonationDto(DonationDto donation);
    public abstract List<DonationDto> dtosFromDonations(List<Donation> donations);
    public abstract void updateDonationFromDto(DonationDto donationDto, @MappingTarget Donation donation);
}
