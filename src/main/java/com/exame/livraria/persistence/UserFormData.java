package com.exame.livraria.persistence;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@NoArgsConstructor
@ToString
public class UserFormData {
	@Getter@Setter private long id = -1;
	@Getter@Setter private String nome;
	@Getter@Setter private String ultimoNome;
	@Getter@Setter private String username;
	@Getter@Setter private String password;
	@Getter@Setter private String passwordCon;
	
	@Builder
	public UserFormData(String nome, String ultimoNome, String username, String password, String passwordCon) {
		super();
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.passwordCon = passwordCon;
	}
	
	public boolean passwordIsValid() {
		if(this.password.equals(passwordCon))
			return true;
		else
			return false;
	}
}
