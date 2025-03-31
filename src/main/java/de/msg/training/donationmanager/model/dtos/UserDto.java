package de.msg.training.donationmanager.model.dtos;

import de.msg.training.donationmanager.model.Campaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import de.msg.training.donationmanager.model.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private List<Campaign> campaignList = new ArrayList<>();
    private boolean active;
    private boolean firstLogin;
    private Long version;

    public UserDto(String firstName, String lastName, String mobileNumber, String username, String mail, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.username = username;
        this.email = mail;
        this.password = password;
    }
}
