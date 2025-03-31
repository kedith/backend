package de.msg.training.donationmanager.repository;

import de.msg.training.donationmanager.model.Campaign;
import de.msg.training.donationmanager.model.Donation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Optional<Donation> findById(Long id);

    List<Donation> findAll();

        @Transactional
    @Modifying
    @Query(value = "DELETE FROM Donation d WHERE d.id = :id ")
    void deleteById(Long id);

    boolean existsByCampaignAndApprovedTrue(Campaign campaign);
}