package de.msg.training.donationmanager.model.mapper;

import de.msg.training.donationmanager.model.Donor;
import de.msg.training.donationmanager.model.dtos.DonorDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class DonorMapper {
    public abstract DonorDto donorDtoFromDonor(Donor donor);

    public abstract Donor donorFromDonorDto(DonorDto donorDto);

    public abstract List<DonorDto> dtosFromDonors(List<Donor> donors);
}
