package com.exame.livraria.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@Table(name = "BOOK",  uniqueConstraints={
		@UniqueConstraint(columnNames = {"NAME", "AUTHOR"})
})
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
@NoArgsConstructor
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BOOK_ID", unique=true, nullable=false)
	@Getter@Setter private long book_Id;

	@Column(nullable=false, length=255)
	@Getter@Setter private String author;

	@Column(nullable=false, length=255)
	@Getter@Setter private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REGISTERED_DATE", nullable=false)
	@Getter@Setter private Date registeredDate;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	@Getter@Setter private User user;

	@Builder
	public Book(String name, String author, Date registeredDate, User user) {
		super();
		this.name = name;
		this.author = author;
		this.registeredDate = registeredDate;
		this.user = user;
	}

}