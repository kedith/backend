package de.msg.training.donationmanager.service;

import de.msg.training.donationmanager.model.Right;
import de.msg.training.donationmanager.model.Role;
import de.msg.training.donationmanager.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl {

    @Autowired
    private final RoleRepository roleRepository ;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Set<Right> findAllRightsByRoles(Role[] roles){
        Set<Right> allRights = new HashSet<>();
        for(Role role : roles){
            Set<Right> thisRolesRights = new HashSet<>(roleRepository.findRightsByRole(role));
            allRights.addAll(thisRolesRights);
        }
        return allRights;
    }

    public void updateRole(Role role) {
         roleRepository.save(role);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public Optional<Role> findById(Long id){return roleRepository.findById(id);}

}
