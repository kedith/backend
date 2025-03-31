package de.msg.training.donationmanager.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="role",
		uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "right_role",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "right_id"))
	private Set<Right> rights = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {

	}

	public Role(Long id, Set<Right> rights, ERole name) {
		this.id = id;
		this.rights = rights;
		this.name = name;
	}

	public Role(ERole name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
	public Set<Right> getRights() {
		return rights;
	}
}