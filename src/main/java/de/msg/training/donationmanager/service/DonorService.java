package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.model.Donation;
import de.msg.training.donationmanager.model.Donor;
import de.msg.training.donationmanager.model.mapper.DonationMapper;
import de.msg.training.donationmanager.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DonorService {
    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private DonationService donationService;

    @Autowired
    private DonationMapper donationMapper;

    public List<Donor> findAll() {
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id).orElse(null);
    }

    public Donor save(Donor donor) {
        return donorRepository.save(donor);
    }

    public Donor update(Donor donor) {
        return donorRepository.save(donor);
    }

    public void deleteById(Long id) throws BusinessException {
        for (Donation donation: donorRepository.getById(id).getDonations()) {
            donation.setBenefactor(null);
            donationService.updateDonation(donationMapper.donationDtoFromDonation(donation), donation.getId());
        }

        donorRepository.deleteById(id);
    }

    public boolean validateFirstLastName(String name){
        if (name == null)
            return false;
        Pattern pattern = Pattern.compile("^[A-Z][a-z]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public boolean validateAdditionalName(String name){
        if (StringUtils.isEmpty(name))
            return true;
        Pattern pattern = Pattern.compile("^[A-Z][a-z]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
