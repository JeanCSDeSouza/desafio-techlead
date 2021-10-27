package com.exame.livraria.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.exame.livraria.persistence.model.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>{
	Book findByName(String name);
	int countByNameAndAuthor(String name, String author);
	List<Book> findByAuthor(String author);
	@Query("SELECT b FROM Book b WHERE b.user.user_Id = :userId")
	List<Book> findByUserId(@Param("userId") Long userId);
}
