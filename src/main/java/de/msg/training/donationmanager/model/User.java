package de.msg.training.donationmanager.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "user",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Principal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String mobileNumber;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_role",
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	private short attempts;

	private String username;

	private String email;

	private String password;

	private String webSocketName;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean firstLogin = true;
	private boolean active = true;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable( name = "user_campaign",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "campaign_id"))
	private List<Campaign> campaignList = new ArrayList<>();

	private Long version;

    public User(String firstName, String lastName, String mobileNumber, String username, String mail, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.username = username;
		this.email = mail;
		this.password = password;
    }

	public User(String webSocketName) {
		this.webSocketName = webSocketName;
	}

	@Override
	public String getName() {
		return webSocketName;
	}
}
