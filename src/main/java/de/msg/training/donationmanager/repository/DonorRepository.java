package de.msg.training.donationmanager.repository;

import de.msg.training.donationmanager.model.Donor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findById(Long id);

    List<Donor> findAll();

    Donor save(Donor donor);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Donor c WHERE c.id = :id ")
    void deleteById(Long id);
}
