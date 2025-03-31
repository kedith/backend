package de.msg.training.donationmanager.controller.right;

import de.msg.training.donationmanager.model.Right;
import de.msg.training.donationmanager.model.Role;
import de.msg.training.donationmanager.service.RightServiceImpl;
import de.msg.training.donationmanager.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/right")
public class RightController {
    @Autowired
    private RightServiceImpl rightService;

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("/getRights")
    public ResponseEntity<List<Right>> getRights(){
        List<Right> rights =  (rightService.findAllRights());
        return new ResponseEntity<>(rights, HttpStatus.OK);
    }

    @GetMapping("/getRightsSelected/{id}")
    public ResponseEntity<List<Right>> getRightsByRole(@PathVariable Long id){
            Role requiredRole=roleService.getRoleById(id);
        List<Right> linkedRights = new ArrayList<>(requiredRole.getRights());
        return new ResponseEntity<>(linkedRights, HttpStatus.OK);
    }

}
