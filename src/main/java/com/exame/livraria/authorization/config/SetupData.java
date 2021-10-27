package com.exame.livraria.authorization.config;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.exame.livraria.persistence.model.Book;
import com.exame.livraria.persistence.model.Role;
import com.exame.livraria.persistence.model.User;
import com.exame.livraria.persistence.repositories.BookRepository;
import com.exame.livraria.persistence.repositories.RoleRepository;
import com.exame.livraria.persistence.repositories.UserRepository;

@Component
public class SetupData {

	@Autowired
	private Environment env;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@PostConstruct
	public void init() {
		initUsers();
		initBooks();
	}

	private void initUsers() {
		if(roleRepository.countByRole("ADMIN") == 0) {
			Role role = Role.builder().role("ADMIN").build();
			roleRepository.save(role);
		}
		if(roleRepository.countByRole("USER") == 0) {
			Role role = Role.builder().role("USER").build();
			roleRepository.save(role);
		}
		if(userRepository.countByUsername("ADMIN") == 0) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(roleRepository.findByRole("ADMIN"));
			User user = User.builder()
					.name("System admin")
					.username("ADMIN")
					.password(env.getProperty("application.admin.password"))//BCrypted password in applicattion.properties
					.enabled(true)
					.lastLogin(Date.from(java.time.ZonedDateTime.now().toInstant()))
					.roles(roles)
					.build();
			userRepository.save(user);
		}if(userRepository.countByUsername("USER-TEST") == 0) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(roleRepository.findByRole("USER"));
			User user2 = User.builder()
					.name("application simple user")
					.username("USER-TEST")
					.password(env.getProperty("application.user.password"))//BCrypted password in applicattion.properties
					.enabled(true)
					.lastLogin(Date.from(java.time.ZonedDateTime.now().toInstant()))
					.roles(roles)
					.build();
			userRepository.save(user2);
		}
	}
	
	private void initBooks() {
		Book book = Book.builder().name("Harry potter e a pedra filosofal")
				.author("JK Rolling")
				.user(userRepository.findByUsername("ADMIN"))
				.registeredDate(Date.from(java.time.ZonedDateTime.now().toInstant()))
				.build();
		if(bookRepository.countByNameAndAuthor(book.getName(), book.getAuthor()) == 0)
			bookRepository.save(book);
		Book book2 = Book.builder().name("Harry potter e o prisioneiro de azkaban")
				.author("JK Rolling")
				.user(userRepository.findByUsername("ADMIN"))
				.registeredDate(Date.from(java.time.ZonedDateTime.now().toInstant()))
				.build();
		if(bookRepository.countByNameAndAuthor(book2.getName(), book2.getAuthor()) == 0)
			bookRepository.save(book2);
		Book book3 = Book.builder().name("Harry potter e o enigma do príncipe")
				.author("JK Rolling")
				.user(userRepository.findByUsername("ADMIN"))
				.registeredDate(Date.from(java.time.ZonedDateTime.now().toInstant()))
				.build();
		if(bookRepository.countByNameAndAuthor(book3.getName(), book3.getAuthor()) == 0)
			bookRepository.save(book3);	
		Book book4 = Book.builder().name("Harry potter e a câmara secreta")
				.author("JK Rolling")
				.user(userRepository.findByUsername("ADMIN"))
				.registeredDate(Date.from(java.time.ZonedDateTime.now().toInstant()))
				.build();
		if(bookRepository.countByNameAndAuthor(book3.getName(), book3.getAuthor()) == 0)
			bookRepository.save(book4);	
	}
}
