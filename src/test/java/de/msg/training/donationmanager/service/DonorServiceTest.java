package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.model.Donor;
import de.msg.training.donationmanager.model.dtos.DonorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DonorServiceTest {
    @Autowired
    DonorService donorService;

    @Test
    void validateFirstLastname() {
        assertTrue(donorService.validateFirstLastName("Harry"));
        assertTrue(donorService.validateFirstLastName("Ju"));
        assertTrue(donorService.validateFirstLastName("Jimmy"));
        assertFalse(donorService.validateFirstLastName(""));
        assertFalse(donorService.validateFirstLastName(null));
        assertFalse(donorService.validateFirstLastName("J"));
        assertFalse(donorService.validateFirstLastName("_Harry"));
        assertFalse(donorService.validateFirstLastName("Harry1"));
        assertFalse(donorService.validateFirstLastName("HarrY"));
        assertFalse(donorService.validateFirstLastName("H@rry"));
        assertFalse(donorService.validateFirstLastName("Harry!"));
        assertFalse(donorService.validateFirstLastName("Harry James"));
        assertFalse(donorService.validateFirstLastName("1Harry"));
        assertFalse(donorService.validateFirstLastName("harry"));
        assertFalse(donorService.validateFirstLastName("H@rry1"));
    }

    @Test
    void validateAdditionalName() {
        assertTrue(donorService.validateAdditionalName("Harry"));
        assertTrue(donorService.validateAdditionalName("Jo"));
        assertTrue(donorService.validateAdditionalName(""));
        assertTrue(donorService.validateAdditionalName(null));
        assertFalse(donorService.validateAdditionalName("_Harry"));
        assertFalse(donorService.validateAdditionalName("J"));
        assertFalse(donorService.validateAdditionalName("HarrY"));
        assertFalse(donorService.validateAdditionalName("harry"));
        assertFalse(donorService.validateAdditionalName("1Harry"));
        assertFalse(donorService.validateAdditionalName("H@rry1"));
        assertFalse(donorService.validateAdditionalName("H@rry"));
        assertFalse(donorService.validateAdditionalName("Harry!"));
    }

    @Test
    void testFindAll() {
        assertNotNull(donorService.findAll());
    }

    @Test
    void testGetDonorById() {
        Donor donor = new Donor(null, "John", "Doe", "", "", null);
        Donor savedDonor = donorService.save(donor);

        Donor newDonor = donorService.getDonorById(savedDonor.getId());

        assertNotNull(newDonor);
    }



}
