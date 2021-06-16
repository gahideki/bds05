package com.devsuperior.movieflix.repository;

import com.devsuperior.movieflix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);

}
