package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.Donation;
import de.msg.training.donationmanager.repository.CampaignRepository;
import de.msg.training.donationmanager.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignServiceImpl {
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    public List<Campaign> findAllCampaigns() {
        return campaignRepository.findAll();
    }
    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id).orElse(null);
    }
    public Campaign getCampaignByName(String name) {
        return campaignRepository.findByName(name).orElse(null);
    }
    public void updateCampaign(Campaign campaign) {
        campaignRepository.update(campaign.getId(),campaign.getName(),campaign.getPurpose(),campaign.getVersion());
    }

    public void saveCampaign(Campaign campaign) {
        campaignRepository.save(campaign);
    }

    public void deleteCampaignById(Long id) throws BusinessException{
        int ok=1;
        List<Donation> donations = donationRepository.findAll();
        Optional<Campaign> campaign=campaignRepository.findById(id);
        Campaign campaign1=campaign.get();
        for(Donation donation:donations){
            if(donation.getCampaign().getId()==campaign1.getId()){
                ok=0;
            }
        }
        if(ok==1){
            campaignRepository.deleteById(id);
        }
        else  throw new BusinessException(BusinessExceptionCode.PAYED_CAMPAIGN);
    }

}
