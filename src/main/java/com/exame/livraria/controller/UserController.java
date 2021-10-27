package com.exame.livraria.controller;



import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.exame.livraria.persistence.UserFormData;
import com.exame.livraria.persistence.model.Role;
import com.exame.livraria.persistence.model.User;
import com.exame.livraria.persistence.repositories.RoleRepository;
import com.exame.livraria.persistence.repositories.UserRepository;

@Controller
public class UserController {
    @Autowired
	UserRepository userRepository;
    
    @Autowired
	RoleRepository roleRepository;
    
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
	
    //This method code isnt optimal, most of it should be in a service and validations needs too be made
    @PostMapping("/auth/create")
    public String create(UserFormData user) {
    	if( user.passwordIsValid() ) {
    		Set<Role> roles = new HashSet<Role>();
    		roles.add(roleRepository.findByRole("USER"));
    		User persistentUser = User.builder().name( user.getNome() + " " + user.getUltimoNome())
        			.username( user.getUsername() )
        			.password(bCryptPasswordEncoder.encode(user.getPassword()))
        			.enabled(true)
        			.lastLogin(Date.from(java.time.ZonedDateTime.now().toInstant()))
        			.roles(roles).build();
    		userRepository.save(persistentUser);
    	}
    	return "redirect:/auth/login";
    }
}
