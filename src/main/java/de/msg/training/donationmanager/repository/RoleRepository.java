package de.msg.training.donationmanager.repository;

import de.msg.training.donationmanager.model.ERole;
import de.msg.training.donationmanager.model.Right;
import de.msg.training.donationmanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   List<Role> findAll();
   Optional<Role> findById(Long id);

   @Query("SELECT r.rights FROM Role r WHERE r = :role")
   Set<Right> findRightsByRole(Role role);

   void delete(Role role);
   void deleteById(Long id);
   Optional<Role> findByName(ERole name);

}
