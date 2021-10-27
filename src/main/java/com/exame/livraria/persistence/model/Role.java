package com.exame.livraria.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
@NoArgsConstructor
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ROLE_ID", unique=true, nullable=false)
	@Getter@Setter private long role_Id;

	@Column(name="ROLE_NAME", nullable=false, length=255)
	@Getter@Setter private String role;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="roles", fetch=FetchType.EAGER)
	private Set<User> users;
	
	@Builder
	public Role(String role) {
		super();
		this.role = role;
	}
}