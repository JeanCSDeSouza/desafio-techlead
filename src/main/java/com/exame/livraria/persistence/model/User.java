package com.exame.livraria.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID", unique=true, nullable=false)
	@Getter@Setter private long user_Id;

	@Column(nullable=false)
	@Getter@Setter private boolean enabled;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_LOGIN", nullable=false)
	@Getter@Setter private Date lastLogin;

	@Column(nullable=false, length=255)
	@Getter@Setter private String name;

	@Column(nullable=false, length=255)
	@Getter@Setter private String password;

	@Column(nullable=false, length=255)
	@Getter@Setter private String username;

	//bi-directional many-to-one association to Book
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	@Getter@Setter private Set<Book> books;

	//bi-directional many-to-many association to Role
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="user_role"
		, joinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="ROLE_ID", nullable=false)
			}
		)
	@Getter@Setter private Set<Role> roles;
	
	@Builder
    public User(String name, String username, String password, Date lastLogin, boolean enabled, Set<Role> roles) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
		this.enabled = enabled;
		this.roles = roles;
	}

}