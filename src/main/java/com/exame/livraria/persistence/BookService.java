package com.exame.livraria.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.exame.livraria.persistence.model.Book;

public interface BookService {
	Book deleteBook(Book book) throws UsernameNotFoundException;
	Book saveBook(Book book);
	List<Book> searcByAuthor(String author);
	Book searchByName(String Name);
	Optional<Book> searchById(Long id);
	Page<Book> findPaginated(Pageable pageable);
}
