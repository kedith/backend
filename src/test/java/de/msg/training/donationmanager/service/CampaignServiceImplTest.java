package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.Donor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CampaignServiceImplTest {
    @Autowired
    CampaignServiceImpl campaignService;
    public final String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETSJdLCJpYXQiOjE2OTMxMjM2MDQsImV4cCI6MTY5MzE1MzYwNH0.Tx3oK0dJdbatgJ0W9ug87WGMLRxUr-YJ9VhJBkx8OHgOaPCDSYktO8b7tWS3kvMwNbtZh_PTe_Dl6MJ567ampg";

    @Test
    void testSaveCampaign() throws BusinessException {
        Campaign campaign=new Campaign(null,"Name111","Purpose1",null, null);
        campaignService.saveCampaign(campaign);
        List<Campaign> list =campaignService.findAllCampaigns();
        assertNotNull(list.contains(campaign));
        List<Campaign> campaigns= campaignService.findAllCampaigns();
        for(Campaign campaign1:campaigns){
            if(campaign1.getName().equals("Name111")){
                campaignService.deleteCampaignById(campaign1.getId());
            }
        }
        //campaignService.deleteById(donorService.findAll().get(donorService.findAll().size()-1).getId());
    }
    @Test
    void testUpdateCampaign() throws BusinessException {
        List<Campaign> campaigns= campaignService.findAllCampaigns();
        for(Campaign campaign:campaigns){
            if(campaign.getName().equals("NameNew1")){
                Campaign campaignUpdate=new Campaign(campaign.getId(),"NameNew1","PurposeNew",null, null);
                campaignService.updateCampaign(campaignUpdate);
                List<Campaign> campaignsNew= campaignService.findAllCampaigns();
                assertNotNull(campaignsNew.contains(campaignUpdate));
                campaignService.deleteCampaignById(campaignUpdate.getId());
            }
        }

    }
    @Test
    void testFindAllCampaigns() throws BusinessException {

        Campaign campaign=new Campaign(null,"wow11","Purpose",null, null);
        campaignService.saveCampaign(campaign);
        assertNotNull(campaignService.findAllCampaigns());
        List<Campaign> campaigns1=campaignService.findAllCampaigns();
        for(Campaign campaign1:campaigns1){

            if(campaign1.getName().equals("wow11")){
                System.out.println(campaign1.getId());
                campaignService.deleteCampaignById(campaign1.getId());
            }
        }
    }
    @Test
    void testGetCampaignById(){
        List<Campaign> campaigns=campaignService.findAllCampaigns();
        assertNotNull(campaignService.getCampaignById(campaigns.get(0).getId()));
    }
}
