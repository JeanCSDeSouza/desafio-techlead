package com.exame.livraria.authorization.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//, securedEnabled = true, jsr250Enabled = true, securedEnabled = true, jsr250Enabled = true
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.exame.livraria"})
@PropertySource("classpath:/application-security.properties")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private WebApplicationContext applicationContext;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;
	
	//not sure why, but spring is overriding UserDetailService, then i'm enforcing the use of the application implementation 
	// of UserDetailService
	@PostConstruct
	public void completeSetup() {
		userDetailsService = applicationContext.getBean(UserDetailsServiceImpl.class);
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(encoder())
		.and().authenticationProvider(authenticationProvider())
		.jdbcAuthentication().dataSource(dataSource);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/resources/**");
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .authorizeRequests()
        .antMatchers("/auth/create","/auth/create-user").permitAll()       
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/auth/login")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/home")
        //.successHandler(customAuthenticationSuccessHandler())
        .failureUrl("/auth/login?error=true")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/auth/perform_logout")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl("/auth/login?logout")
        .permitAll();
    }
    
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
}
