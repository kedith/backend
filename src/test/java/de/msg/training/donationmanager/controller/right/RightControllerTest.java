package de.msg.training.donationmanager.controller.right;

import de.msg.training.donationmanager.controller.right.RightController;
import de.msg.training.donationmanager.model.Right;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RightControllerTest {
    @Autowired
    private RightController rightController ;

    @Test
    void testGetRights(){
        ResponseEntity<List<Right>> responseEntityValid = rightController.getRights();
        assertNotNull(responseEntityValid);
        assertEquals(HttpStatus.OK, responseEntityValid.getStatusCode());
        ResponseEntity<List<Right>> responseEntityValid1 = rightController.getRightsByRole(1L);
        assertNotNull(responseEntityValid1);
        assertEquals(HttpStatus.OK, responseEntityValid1.getStatusCode());
    }

    @Test
    void testGetRighsByRole() {

    }
}
