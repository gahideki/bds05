package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repository.UserRepository;
import com.devsuperior.movieflix.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    public UserDTO getRoleUserLogged() {
        User userAuthenticated = authenticated();
        return new UserDTO(userAuthenticated);
    }

    private User authenticated() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = repository.findByEmail(username);
            return user;
        } catch (Exception ex) {
            throw new UnauthorizedException("Invalid user");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if(ObjectUtils.isEmpty(user)) {
            logger.error("User not found {}", username);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User {} found", username);
        return user;
    }

}
