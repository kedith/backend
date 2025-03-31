package de.msg.training.donationmanager.controller.campaign;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.dtos.CampaignDto;
import de.msg.training.donationmanager.model.mapper.CampaignMapper;
import de.msg.training.donationmanager.service.AuthorizationServiceImpl;
import de.msg.training.donationmanager.service.CampaignServiceImpl;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static de.msg.training.donationmanager.model.ERight.CAMP_MANAGEMENT;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    @Autowired
    private CampaignServiceImpl campaignService;

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    public AuthorizationServiceImpl authorizationService;
    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaigns(@RequestHeader("Authorization") String jwtToken){
        if(authorizationService.checkRepRestriction(jwtToken)){
            return new ResponseEntity<>(authorizationService.UserFromJwt(jwtToken).getCampaignList(),HttpStatus.OK);
        }
        else {
            List<Campaign> campaigns = (campaignService.findAllCampaigns());
            return new ResponseEntity<>(campaigns, HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) throws BusinessException {
        Campaign campaign = campaignService.getCampaignById(id);
        if (campaign == null) {
            throw new BusinessException(BusinessExceptionCode.CAMPAIGN_NOT_FOUND);
        }
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }

    @GetMapping("/getcampaign/{name}")
    public ResponseEntity<Campaign> getCampaignByName(@PathVariable String name) throws BusinessException {
        Campaign campaign = campaignService.getCampaignByName(name);
        if (campaign == null) {
            throw new BusinessException(BusinessExceptionCode.CAMPAIGN_NOT_FOUND);
        }
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CampaignDto> saveCampaign(@RequestBody CampaignDto campaignDto,@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken,CAMP_MANAGEMENT);
        if(campaignDto.getName().isEmpty()|| campaignDto.getPurpose().isEmpty()){
            throw new BusinessException(BusinessExceptionCode.INVALID_CAMPAIGN);
        }
        else{
            List<Campaign> campaigns=campaignService.findAllCampaigns();
            for(Campaign campaign:campaigns){
                if(campaignDto.getName().equals(campaign.getName()))
                    throw new BusinessException(BusinessExceptionCode.INVALID_CAMPAIGN);
            }
            campaignService.saveCampaign(campaignMapper.campaignFromCampaignDto(campaignDto));
            return new ResponseEntity<>(campaignDto, HttpStatus.OK);
        }
    }
    @PutMapping("/{id}")
    public void updateCampaignById(@PathVariable Long id, @RequestBody CampaignDto campaignToReplace,@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken,CAMP_MANAGEMENT);
        if(campaignToReplace.getName().isEmpty() || campaignToReplace.getPurpose().isEmpty()){
            throw new BusinessException(BusinessExceptionCode.INVALID_CAMPAIGN);
        }
        else {
            try {
                campaignToReplace.setId(id);
                List<Campaign> campaigns=campaignService.findAllCampaigns();
                for(Campaign campaign:campaigns){
                    if(campaign.getId()==id){
                        campaignToReplace.setVersion(campaign.getVersion());
                    }
                }

                campaignToReplace.setVersion(campaignToReplace.getVersion()+1);
                Campaign campaign=campaignMapper.campaignFromCampaignDto(campaignToReplace);
                campaign.setVersion(campaignToReplace.getVersion());
                campaignService.updateCampaign(campaign);
            }catch (OptimisticLockException ex){
                throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaignById(@PathVariable Long id,@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken,CAMP_MANAGEMENT);
        if(campaignService.getCampaignById(id) == null){
            throw new BusinessException(BusinessExceptionCode.CAMPAIGN_NOT_FOUND);
        }

        try {
            campaignService.deleteCampaignById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(OptimisticLockException ex){
            throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
        }
    }
}
