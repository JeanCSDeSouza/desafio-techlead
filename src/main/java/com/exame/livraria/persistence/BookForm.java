package com.exame.livraria.persistence;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class BookForm {
	@Getter@Setter private long book_Id = -1;
	@Getter@Setter private String author;
	@Getter@Setter private String name;
	@Getter@Setter private Date registeredDate = Date.from(java.time.ZonedDateTime.now().toInstant());	
	
	@Builder
	public BookForm(long book_Id, String name, String author, Date registeredDate) {
		this.book_Id = book_Id;
		this.name = name;
		this.author = author;
		this.registeredDate = registeredDate;
	}
}
