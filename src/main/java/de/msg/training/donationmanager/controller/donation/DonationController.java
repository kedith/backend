package de.msg.training.donationmanager.controller.donation;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.ENotifications;
import de.msg.training.donationmanager.model.Notifications;
import de.msg.training.donationmanager.model.dtos.DonationDto;
import de.msg.training.donationmanager.model.mapper.DonationMapper;
import de.msg.training.donationmanager.service.AuthorizationServiceImpl;
import de.msg.training.donationmanager.service.DonationService;
import de.msg.training.donationmanager.service.NotificationService;
import de.msg.training.donationmanager.service.UserDetailsServiceImpl;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.msg.training.donationmanager.model.ERight.*;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;
    @Autowired
    public AuthorizationServiceImpl authorizationService;
    @Autowired
    private DonationMapper donationMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public ResponseEntity<List<DonationDto>> findAll(@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        if (authorizationService.checkRepRestriction(jwtToken)) {
            authorizationService.checkUserAuthorization(jwtToken,CAMP_REPORT_RESTRICTED);
            List<Campaign> campaigns = authorizationService.UserFromJwt(jwtToken).getCampaignList();
            Set<DonationDto> donations = new HashSet<>();
            for (Campaign i : campaigns) {
                donations.addAll(donationMapper.dtosFromDonations(i.getDonations()));
            }
            return new ResponseEntity<>(donations.stream().toList(), HttpStatus.OK);
        } else {
            authorizationService.checkUserAuthorization(jwtToken, DONATION_REPORTING);
            List<DonationDto> donationDtos = donationService.findAll();
            return new ResponseEntity<>(donationDtos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDto> getDonationById(@PathVariable Long id, @RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken, DONATION_REPORTING);
        DonationDto donationDto = donationService.getDonationById(id);
        if (donationDto == null) {
            throw new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND);
        }
        return ResponseEntity.ok(donationDto);
    }

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(@RequestBody DonationDto donationDto, @RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken, DONATION_MANAGEMENT);
        if (!donationService.isValid(donationDto))
            throw new BusinessException(BusinessExceptionCode.INVALID_DONATION);

        DonationDto createdDonation = donationService.createDonation(donationDto);
        return ResponseEntity.ok(createdDonation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDonation(@PathVariable Long id, @RequestBody DonationDto updatedDonation, @RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken, DONATION_MANAGEMENT);
        DonationDto existingDonation = donationService.getDonationById(id);

        if (existingDonation == null) {
            throw new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND);
        }
        try {
            if(existingDonation.isApproved() != updatedDonation.isApproved())
            {
                notificationService.saveNotification(new Notifications(userDetailsService.getUserByUsername(updatedDonation.getCreatedBy()),null, ENotifications.DONATION_APPROVED,updatedDonation.getAmount().toString(), LocalDateTime.now()));
            }
            donationService.updateDonation(updatedDonation, id);
            return ResponseEntity.ok().build();
        } catch (OptimisticLockException ex) {
            throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Void> approveDonation(@PathVariable Long id, @RequestBody String name,@RequestHeader("Authorization") String jwtToken) throws BusinessException {
        authorizationService.checkUserAuthorization(jwtToken,DONATION_MANAGEMENT);
        DonationDto existingDonation = donationService.getDonationById(id);
        if (existingDonation == null) {
            throw new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND);
        }

        try {
            donationService.approveDonation(id, name);
            return ResponseEntity.ok().build();
        } catch (OptimisticLockException ex) {
            throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id, @RequestHeader("Authorization") String jwtToken) throws BusinessException {

        authorizationService.checkUserAuthorization(jwtToken, DONATION_MANAGEMENT);
        DonationDto donation = donationService.getDonationById(id);
        if (donation == null || donation.isApproved()) {
            throw new BusinessException(BusinessExceptionCode.DONATION_NOT_FOUND);
        }
        try {
            donationService.deleteDonation(id);
            return ResponseEntity.ok().build();
        } catch (OptimisticLockException ex) {
            throw new BusinessException(BusinessExceptionCode.CONCURRENCY_ERROR);
        }
    }
}