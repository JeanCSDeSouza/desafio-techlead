package com.exame.livraria.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exame.livraria.persistence.exception.BookNotFoundException;
import com.exame.livraria.persistence.model.Book;
import com.exame.livraria.persistence.repositories.BookRepository;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	
	@PostAuthorize("hasAuthority('ADMIN') or returnObject.user.username == principal.username")
	@Transactional
	public Book deleteBook(Book book) throws BookNotFoundException {
		book.getUser().getUsername();//to ensure the book is filled with its user data 
		if(bookRepository.existsById(book.getBook_Id())) {	
			System.out.println("lalala3");
			bookRepository.delete(book);
			return book;
		}else {
			throw new BookNotFoundException("Book does not exists");
		}
	}
	//@PreFilter("hasRole('ROLE_ADMIN') or filterObject.user.username == authentication.principal.username")
	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> searcByAuthor(String author) {
		// TODO Auto-generated method stub
		return bookRepository.findByAuthor(author);
	}


	@Override
	public Book searchByName(String Name) {
		// TODO Auto-generated method stub
		return bookRepository.findByName(Name);
	}

	@Override
	public Optional<Book> searchById(Long id) throws BookNotFoundException{
		// TODO Auto-generated method stub
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			return book;
		}else {
			throw new BookNotFoundException("Book does not exists");
		}
	}
	
	public Page<Book> findPaginated(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable); 
        return bookPage;

    }


}
