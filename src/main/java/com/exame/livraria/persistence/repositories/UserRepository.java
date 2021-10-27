package com.exame.livraria.persistence.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.exame.livraria.persistence.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    List<User> findByName(String name);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.username = :username")
    int countByUsername(@Param("username") String username);

    @Query("UPDATE User u SET u.lastLogin=:lastLogin WHERE u.username = ?#{ principal?.username }")
    @Modifying
    @Transactional
    void updateLastLogin(@Param("lastLogin") Date lastLogin);
}
