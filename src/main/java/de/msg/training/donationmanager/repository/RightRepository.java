package de.msg.training.donationmanager.repository;

import de.msg.training.donationmanager.model.ERight;
import de.msg.training.donationmanager.model.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RightRepository extends JpaRepository<Right, Long> {
    Optional<Right> findByName(ERight right);
    List<Right> findAll();
    Right save(Right right);
    void deleteById(Long id);
}
