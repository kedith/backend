package de.msg.training.donationmanager.controller.role;

import de.msg.training.donationmanager.controller.right.RightController;
import de.msg.training.donationmanager.controller.role.RoleController;
import de.msg.training.donationmanager.model.Right;
import de.msg.training.donationmanager.model.Role;
import de.msg.training.donationmanager.model.mapper.CampaignMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest

public class RoleControllerTest {
    @Autowired
    private RoleController roleController  ;

    @Test
    void testGetRoles(){
        ResponseEntity<List<Role>> responseEntityValid = roleController.getRoles();
        assertNotNull(responseEntityValid);
        assertEquals(HttpStatus.OK, responseEntityValid.getStatusCode());
        ResponseEntity<Role> responseEntityValid1 = roleController.getRolesById(1L);
        assertNotNull(responseEntityValid1);
        assertEquals(HttpStatus.OK, responseEntityValid1.getStatusCode());
    }

    @Test
    void testSetPermissions() {

    }

    @Test
    void updateRoleById() {
        ResponseEntity<Role> responseEntity=roleController.getRolesById(1L);
        Role role=responseEntity.getBody();
        Role[] roles = new Role[1];
        roles[0]=role;
        ResponseEntity<Set<Right>> responseEntity1=roleController.getRightsByRoles(roles);
        Set<Right> rights=responseEntity1.getBody();
        roleController.updateRoleById(1L,rights);

    }

    @Test
    void testGetRolesById() {
        ResponseEntity<Role> responseEntity=roleController.getRolesById(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetRightsByRoles() {
        ResponseEntity<Role> responseEntity=roleController.getRolesById(1L);
        Role role=responseEntity.getBody();
        Role[] roles = new Role[1];
        roles[0]=role;
        ResponseEntity<Set<Right>> responseEntity1=roleController.getRightsByRoles(roles);
        assertNotNull(responseEntity1);
        assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
    }
}
