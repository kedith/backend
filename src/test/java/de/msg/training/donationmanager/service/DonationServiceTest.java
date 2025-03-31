package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.Donor;
import de.msg.training.donationmanager.model.ECurrency;
import de.msg.training.donationmanager.model.dtos.DonationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DonationServiceTest {
    @Autowired
    DonationService donationService;
    public final String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETSJdLCJpYXQiOjE2OTMxMjM2MDQsImV4cCI6MTY5MzE1MzYwNH0.Tx3oK0dJdbatgJ0W9ug87WGMLRxUr-YJ9VhJBkx8OHgOaPCDSYktO8b7tWS3kvMwNbtZh_PTe_Dl6MJ567ampg";

    @Test
    void validDonationTest() {
        DonationDto validDonation = new DonationDto();
        setValidDonation(validDonation);

        DonationDto invalidDonation = new DonationDto();
        setInvalidDonation(invalidDonation);

        assertTrue(donationService.isValid(validDonation));
        assertFalse(donationService.isValid(invalidDonation));
    }

    static void setValidDonation(DonationDto validDonation) {
        validDonation.setId(Long.valueOf(2));
        validDonation.setAmount(100.0);
        validDonation.setCurrency(ECurrency.USD);
        validDonation.setCampaign(new Campaign());
        validDonation.setCreatedBy("AB123");
        validDonation.setCreatedDate(LocalDate.now().minusDays(1));
        validDonation.setBenefactor(new Donor(Long.valueOf(2), "Joe", "Schmoe", "", "", new ArrayList())); // Set a valid Donor object
        validDonation.setApprovedBy("CD456");
        validDonation.setApprovedDate(LocalDate.now());
        validDonation.setApproved(true);
        validDonation.setNotes("Note");
    }

    static void setInvalidDonation(DonationDto invalidDonation) {
        invalidDonation.setAmount(-50.0);
        invalidDonation.setCurrency(null);
        invalidDonation.setCampaign(null);
        invalidDonation.setCreatedBy("123");
        invalidDonation.setCreatedDate(LocalDate.now());
        invalidDonation.setApprovedBy("AB123");
        invalidDonation.setApprovedDate(LocalDate.now().minusDays(1));
        invalidDonation.setApproved(true);
        invalidDonation.setNotes("Note");
    }
}
