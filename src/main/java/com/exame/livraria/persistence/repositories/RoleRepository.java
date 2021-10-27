package com.exame.livraria.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.exame.livraria.persistence.model.Role;
public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByRole(String role);
	
	int  countByRole(String role);
}
