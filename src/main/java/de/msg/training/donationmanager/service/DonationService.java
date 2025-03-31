package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Donation;
import de.msg.training.donationmanager.model.dtos.DonationDto;
import de.msg.training.donationmanager.model.mapper.DonationMapper;
import de.msg.training.donationmanager.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public DonationService(DonationRepository donationRepository, DonationMapper donationMapper, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.donationRepository = donationRepository;
        this.donationMapper = donationMapper;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public List<DonationDto> findAll() {
        return donationMapper.dtosFromDonations(donationRepository.findAll());
    }

    public DonationDto createDonation(DonationDto donationDto) {
        Donation donation = donationMapper.donationFromDonationDto(donationDto);
        Donation savedDonation = donationRepository.save(donation);
        return donationMapper.donationDtoFromDonation(savedDonation);
    }

    public DonationDto getDonationById(Long id) {
        return donationRepository.findById(id)
                .map(donationMapper::donationDtoFromDonation)
                .orElse(null);
    }

    public void updateDonation(DonationDto updatedDonationDto, Long id) throws BusinessException {
        Donation existingDonation = donationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND));
        List<Donation> donations=donationRepository.findAll();
        int ok=0;
        for(Donation donation:donations){
            if(Objects.equals(donation.getId(), id) && updatedDonationDto.isApproved()==FALSE){
                donationMapper.updateDonationFromDto(updatedDonationDto, existingDonation);
                existingDonation.setId(id);
                donationRepository.save(existingDonation);ok=1;
                break;
            }
        }
        if(ok==0)
            throw  new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND);
    }

    public void deleteDonation(Long id) throws BusinessException {
        if (!donationRepository.existsById(id)) {

            throw new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND);
        }
        donationRepository.deleteById(id);
    }

    public void approveDonation(Long id, String name) throws BusinessException {
        DonationDto donation = getDonationById(id);
        if (donation == null) {
            throw new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND);
        }
        if (name.equals(donation.getApprovedBy())) {
            throw new BusinessException(BusinessExceptionCode.USER_CANNOT_APPROVE_DONATION);
        }
        if (userDetailsServiceImpl.getUserByUsername(name) == null || name.isEmpty()) {
            throw new BusinessException(BusinessExceptionCode.USER_NOT_FOUND);
        }
        donation.setApproved(true);
        donation.setApprovedBy(name);
        donation.setApprovedDate(LocalDate.now());
        donationRepository.save(donationMapper.donationFromDonationDto(donation));
    }

    public boolean isValid(DonationDto donation) {
        if (donation.getAmount() == null || donation.getAmount() <= 0) {
            return false;
        }
        if (donation.getCurrency() == null) {
            return false;
        }
        if (donation.getCampaign() == null) {
            return false;
        }
        if (donation.getCreatedBy() == null || donation.getCreatedBy().isEmpty() ||
                !Pattern.matches("^[A-Za-z]{2,6}[0-9]*$", donation.getCreatedBy()))
            return false;

        if (donation.getCreatedDate() == null)
            return false;

        if (donation.getBenefactor() == null) {
            return false;
        }

        if (donation.getApprovedBy() != null && donation.getApprovedBy().equals(donation.getCreatedBy()))
            return false;

        if (donation.getApprovedDate() != null && donation.getApprovedDate().isBefore(donation.getCreatedDate())) {
            return false;
        }

        return true;
    }
}
