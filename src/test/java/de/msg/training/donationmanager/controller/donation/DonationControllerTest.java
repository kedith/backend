package de.msg.training.donationmanager.controller.donation;

import de.msg.training.donationmanager.controller.donor.DonorController;
import de.msg.training.donationmanager.exception.BusinessException;
import de.msg.training.donationmanager.exception.BusinessExceptionCode;
import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.Donation;
import de.msg.training.donationmanager.model.Donor;
import de.msg.training.donationmanager.model.ECurrency;
import de.msg.training.donationmanager.model.dtos.DonationDto;
import de.msg.training.donationmanager.model.dtos.DonorDto;
import de.msg.training.donationmanager.model.mapper.DonationMapper;
import de.msg.training.donationmanager.model.mapper.DonationMapperImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Boolean.FALSE;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DonationControllerTest {
    @Autowired
    DonationController donationController;
    public final String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYW5hZ00iLCJyb2xlcyI6WyJNR04iXSwiaWF0IjoxNjkzMjAyMzMzLCJleHAiOjE2OTMyMzIzMzN9.QTwULvBjvTvktesWMP_vIDAjJoGUFUFLcltUy49SuRM1NqBUAyE3EpAS1mpTGq5K2P4xs-GDLByj_VvPqz1PSw";

    @Test
    void testUpdateDonation() throws BusinessException {
        DonationMapper donationMapper=new DonationMapperImpl();
        LocalDate currentDate = LocalDate.now();LocalDate currentDate1 = LocalDate.of(2023, Month.AUGUST,30);
        Donation donation = new Donation(1000005L,50.00, ECurrency.EUR,new Campaign(1L,"Wertryrr","errtrr",null,0L),"Johan",currentDate,new Donor(10000L,"","Joe","Shnoe", "",null,0L),FALSE,"Janos",currentDate1, "Nota",0L);
        BusinessException exception = assertThrows(BusinessException.class, () -> donationController.updateDonation(1000005L,donationMapper.donationDtoFromDonation(donation), token));
        assertEquals(BusinessExceptionCode.DONATION_NOT_FOUND, exception.getBusinessExceptionCode());
        Donation invalidDonation = new  Donation(1000019L,50.00, ECurrency.EUR,new Campaign(1L,"Wertryrr","errtrr",null,0L),"Johan",currentDate,new Donor(10000L,"","Joe","Shnoe", "",null,0L),FALSE,"",currentDate, "Nota",0L);
        BusinessException exception1 = assertThrows(BusinessException.class, () -> donationController.deleteDonation(1000005L, token));
        assertEquals(BusinessExceptionCode.DONATION_NOT_FOUND, exception1.getBusinessExceptionCode());
    }
    @Test
    void testDeleteDonation() throws BusinessException {
        DonationMapper donationMapper=new DonationMapperImpl();
        LocalDate currentDate = LocalDate.now();LocalDate currentDate1 = LocalDate.of(2023, Month.AUGUST,30);
        Donation invalidDonation = new  Donation(1000019L,50.00, ECurrency.EUR,new Campaign(1L,"Wertryrr","errtrr",null,0L),"Johan",currentDate,new Donor(10000L,"","Joe","Shnoe", "",null,0L),FALSE,"",currentDate, "Nota",0L);
        BusinessException exception1 = assertThrows(BusinessException.class, () -> donationController.deleteDonation(1000005L, token));
        assertEquals(BusinessExceptionCode.DONATION_NOT_FOUND, exception1.getBusinessExceptionCode());
    }
    @Test
    void testFindAll() throws BusinessException{
        ResponseEntity<List<DonationDto>> donations=donationController.findAll(token);
        assertNotNull(donations);
        assertEquals(HttpStatus.OK, donations.getStatusCode());
    }
    @Test
    void testGetDonationById() throws BusinessException{
        BusinessException exception = assertThrows(BusinessException.class, () -> donationController.getDonationById(209548743856837287L, token));
        assertEquals(BusinessExceptionCode.DONATION_NOT_FOUND, exception.getBusinessExceptionCode());
    }
}
