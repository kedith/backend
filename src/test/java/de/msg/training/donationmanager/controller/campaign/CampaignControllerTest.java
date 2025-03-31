package de.msg.training.donationmanager.controller.campaign;

import de.msg.training.donationmanager.controller.campaign.CampaignController;
import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.dtos.CampaignDto;
import de.msg.training.donationmanager.model.mapper.CampaignMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class CampaignControllerTest {
    @Autowired
    private CampaignController campaignController ;

    @Autowired
    CampaignMapper campaignMapper;
    public final String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYW5hZ00iLCJyb2xlcyI6WyJNR04iXSwiaWF0IjoxNjkzMjAyMzMzLCJleHAiOjE2OTMyMzIzMzN9.QTwULvBjvTvktesWMP_vIDAjJoGUFUFLcltUy49SuRM1NqBUAyE3EpAS1mpTGq5K2P4xs-GDLByj_VvPqz1PSw";
    @Test
    void testSaveCampaign() throws BusinessException {
        CampaignDto campaignDto=new CampaignDto(null,"Human1111321","Children",0L);
        ResponseEntity<CampaignDto> responseEntityValid = campaignController.saveCampaign(campaignDto,token);

        assertNotNull(responseEntityValid);
        assertEquals(HttpStatus.OK, responseEntityValid.getStatusCode());

        ResponseEntity
                <List<Campaign>> campaigns=  campaignController.getCampaigns(token);
        for(Campaign campaign:campaigns.getBody()){
            if(campaign.getName().equals("Human1111321"))campaignController.deleteCampaignById(campaign.getId(),token);
        }
    }

    @Test
    void testGetCampaigns(){
        ResponseEntity<List<Campaign>> responseEntityValid = campaignController.getCampaigns(token);
        assertNotNull(responseEntityValid);
    }

    @Test
    void testGetCampaignById() throws BusinessException {
        ResponseEntity<List<Campaign>> campaignsUri=campaignController.getCampaigns(token);
        List<Campaign> campaigns=campaignsUri.getBody();
        ResponseEntity<Campaign> responseEntityValid = campaignController.getCampaignById(campaigns.get(0).getId());
        assertNotNull(responseEntityValid);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            campaignController.getCampaignById(100000000L);
        });
        assertEquals(BusinessExceptionCode.CAMPAIGN_NOT_FOUND, exception.getBusinessExceptionCode());
    }

    @Test
    void testGetCampaignByName() throws BusinessException {
        CampaignDto campaignDto=new CampaignDto(null,"Human111132133","Children",0L);
        ResponseEntity<CampaignDto> responseEntityValid = campaignController.saveCampaign(campaignDto,token);
        ResponseEntity<Campaign> responseEntityValid1 = campaignController.getCampaignByName("Human111132133");
        assertNotNull(responseEntityValid1);
        Campaign campaignul=responseEntityValid1.getBody();
        campaignController.deleteCampaignById(campaignul.getId(),token);
    }

    @Test
    void testUpdateCampaignById() throws BusinessException {
        ResponseEntity<List<Campaign>> campaignsUri=campaignController.getCampaigns(token);
        List<Campaign> campaigns=campaignsUri.getBody();
        CampaignDto campaignDtoUpdate=campaignMapper.campaignDtoFromCampaign(campaigns.get(0));
        campaignDtoUpdate.setPurpose("macarena");
        campaignController.updateCampaignById(campaigns.get(0).getId(),campaignDtoUpdate,token);
        CampaignDto invalidCampaignDtoUpdate=new CampaignDto(null,"","Dogo",0L);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            campaignController.saveCampaign(invalidCampaignDtoUpdate,token);
        });
         }

    @Test
    void testDeleteCampaignById() throws BusinessException {
        CampaignDto campaignDto=new CampaignDto(null,"Human111131","Children",0L);
        ResponseEntity<CampaignDto> responseEntityValid = campaignController.saveCampaign(campaignDto,token);
        assertNotNull(responseEntityValid);
        assertEquals(HttpStatus.OK, responseEntityValid.getStatusCode());
        ResponseEntity
                <List<Campaign>> campaigns=  campaignController.getCampaigns(token);
        for(Campaign campaign:campaigns.getBody()){
            if(campaign.getName().equals("Human111131"))campaignController.deleteCampaignById(campaign.getId(),token);
        }
    }
}
