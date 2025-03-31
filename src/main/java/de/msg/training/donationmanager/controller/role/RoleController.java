package de.msg.training.donationmanager.controller.role;

import de.msg.training.donationmanager.model.Right;
import de.msg.training.donationmanager.model.Role;
import de.msg.training.donationmanager.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getRoles(){
        List<Role> roles =  (roleService.findAll());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/setRoles")
    public void setPermissions(@RequestBody Role role) {
        roleService.saveRole(role);
    }
    @PutMapping("/updateRoles/{id}")
    public void updateRoleById(@PathVariable Long id, @RequestBody Set<Right> right){
        Optional<Role> role= roleService.findById(id);
        List<Role> roles=roleService.findAll();
        Role role1=new Role();
        for(Role rolee:roles){
            if(rolee.getId() == id){
                 role1=new Role(id,right,rolee.getName());
            }
        }
        roleService.updateRole(role1);
    }

    @GetMapping("/getRoles/{id}")
    public ResponseEntity<Role> getRolesById(@RequestParam Long id){
        Role role = (roleService.getRoleById(id));
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PostMapping("/getRightsForRoles")
    public ResponseEntity<Set<Right>> getRightsByRoles(@RequestBody Role[] roles){
        Set<Right> rights = roleService.findAllRightsByRoles(roles);
        return new ResponseEntity<>(rights, HttpStatus.OK);
    }
}
