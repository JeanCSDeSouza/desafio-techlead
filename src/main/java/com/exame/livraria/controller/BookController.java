package com.exame.livraria.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.exame.livraria.authorization.config.UserDetailsImpl;
import com.exame.livraria.persistence.BookForm;
import com.exame.livraria.persistence.BookService;
import com.exame.livraria.persistence.exception.BookNotFoundException;
import com.exame.livraria.persistence.model.Book;
import com.exame.livraria.persistence.model.User;
import com.exame.livraria.persistence.repositories.UserRepository;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private UserRepository userRepository;   
	/**
	 * Lists all books
	 * @param model
	 * @param page page number
	 * @param size quantity of itens in the page
	 * @return
	 */
	@GetMapping(value = "/books")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listBooks(ModelMap model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) throws BookNotFoundException{
		Page<Book> bookPage = bookService.findPaginated(PageRequest.of(page.orElse(1)-1, size.orElse(5)));
		model.addAttribute("bookPage", bookPage);

		int totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return new ModelAndView("forward:/list",  model);
	}
	@GetMapping(value = "/books/{book.id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView getBooks(ModelMap model, @PathVariable("book.id") long book_id) throws BookNotFoundException{
		Optional<Book> book = bookService.searchById(book_id);
		if(book.isPresent()) {
			model.addAttribute("Book",book);
		}		
		return new ModelAndView("forward:/list",  model);
	}

	/**
	 * Deletes a book by its unique Id
	 * Metthod simplified to be called by a get url in the
	 * but shhould be method = RequestMethod.DELETE
	 * @param model
	 * @param book_id unique if of a book
	 * @return
	 */
	@GetMapping(value ="/books/delete/{book.id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String deleteBook(@PathVariable("book.id") long book_id) {
		Optional<Book> book = bookService.searchById(book_id);
		if( book.isPresent()) {
			bookService.deleteBook(book.get());
		}
		return "forward:/books";
	}
	@PostMapping(value = "/books/book/persistNew")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String saveBook(BookForm book, @AuthenticationPrincipal UserDetailsImpl user) {
		if(book.getBook_Id() == -1) {
			User persistedUser = userRepository.findByUsername(user.getUsername());
			Book persistedBook = Book.builder().author(book.getAuthor()).name(book.getName()).registeredDate(book.getRegisteredDate()).user(persistedUser).build();
			bookService.saveBook(persistedBook);
		}
		return "redirect:/books";
	}
}
