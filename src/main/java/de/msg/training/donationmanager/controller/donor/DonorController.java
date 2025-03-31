package de.msg.training.donationmanager.controller.donor;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;

import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.Donation;
import de.msg.training.donationmanager.model.Donor;
import de.msg.training.donationmanager.model.dtos.DonorDto;
import de.msg.training.donationmanager.model.mapper.DonorMapper;
import de.msg.training.donationmanager.service.AuthorizationServiceImpl;
import de.msg.training.donationmanager.service.CampaignServiceImpl;
import de.msg.training.donationmanager.service.DonorService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.msg.training.donationmanager.model.ERight.BENEF_MANAGEMENT;

@RestController
@RequestMapping("/donors")
public class DonorController {
    @Autowired
    private DonorService donorService;

    @Autowired
    private DonorMapper donorMapper;

    @Autowired
    private CampaignServiceImpl campaignService;

    @Autowired
    public AuthorizationServiceImpl authorizationService;
    @GetMapping
    public ResponseEntity<List<DonorDto>> findAll(@RequestHeader("Authorization") String jwtToken) {
        if(authorizationService.checkRepRestriction(jwtToken)){
            List<Campaign> campaigns = authorizationService.UserFromJwt(jwtToken).getCampaignList();
            Set<Donor> donors = new HashSet<>();
            for(Campaign i:campaigns){
                for(Donation j:i.getDonations()){
                    donors.add(j.getBenefactor());
                }

            }
            List<Donor>donorsList = donors.stream().toList();
            return new ResponseEntity<>(donorMapper.dtosFromDonors(donorsList),HttpStatus.OK);
        }
        else {
            List<DonorDto> donorDtos = donorMapper.dtosFromDonors(donorService.findAll());
            return new ResponseEntity<>(donorDtos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorDto> getDonorById(@PathVariable Long id) throws BusinessException {
        DonorDto donorDto = donorMapper.donorDtoFromDonor(donorService.getDonorById(id));
        if (donorDto == null){
            throw new BusinessException(BusinessExceptionCode.DONOR_NOT_FOUND);
        }
        return new ResponseEntity<>(donorDto, HttpStatus.OK);
    }

    @GetMapping("/campaign/{id}")
    public ResponseEntity<Set<Donor>> getDonorByCampaignId(@PathVariable Long id) {
        List<Donation> donations = campaignService.getCampaignById(id).getDonations();
        Set<Donor> donors = new HashSet<>();

        for(Donation donation: donations) {
            if (donation.getBenefactor() != null)
                donors.add(donation.getBenefactor());
        }

        return new ResponseEntity<>(donors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DonorDto> createDonor(@RequestBody DonorDto donorDto,@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken,BENEF_MANAGEMENT);
        if(donorService.validateFirstLastName(donorDto.getFirstName()) && donorService.validateFirstLastName(donorDto.getLastName()) && donorService.validateAdditionalName(donorDto.getAdditionalName()) && donorService.validateAdditionalName(donorDto.getMaidenName())){
            DonorDto donor = donorMapper.donorDtoFromDonor(donorService.save(donorMapper.donorFromDonorDto(donorDto)));
            return new ResponseEntity<>(donor, HttpStatus.CREATED);
        } else {
            throw new BusinessException(BusinessExceptionCode.INVALID_DONOR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateDonor(@RequestBody DonorDto updatedDonor,@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken,BENEF_MANAGEMENT);
        if (donorService.getDonorById(updatedDonor.getId()) == null){
            throw new BusinessException(BusinessExceptionCode.DONOR_NOT_FOUND);
        }

        try {
            donorService.update(donorMapper.donorFromDonorDto(updatedDonor));
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(OptimisticLockException ex){
            throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable("id")Long id,@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken,BENEF_MANAGEMENT);
        if(donorService.getDonorById(id) == null){
            throw new BusinessException(BusinessExceptionCode.DONOR_NOT_FOUND);
        }

        try {
            donorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (OptimisticLockException ex){
            throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
        }
    }
}
