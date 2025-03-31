package de.msg.training.donationmanager.repository;

import de.msg.training.donationmanager.model.Campaign;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findAll();
    Campaign save(Campaign campaign);
    @Transactional
    @Modifying
    @Query(value = "UPDATE  Campaign c SET c.id=:id, c.name= :name , c.purpose =:purpose, c.version=:version WHERE c.id = :id ")
    void update(@Param("id") Long id, @Param("name") String name, @Param("purpose") String purpose,@Param("version") Long version);

    Optional<Campaign> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Campaign c WHERE c.id = :id ")
    void deleteById(Long id);
}
