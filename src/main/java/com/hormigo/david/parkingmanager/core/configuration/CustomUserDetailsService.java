package com.hormigo.david.parkingmanager.core.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.user.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        com.hormigo.david.parkingmanager.user.domain.User user = null;
        try {
            user = userService.getByEmail(username);
        }
        catch (UserDoesNotExistsException ex) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails details = User.withUsername(user.getEmail())
            .password(user.getPassword())
            .accountExpired(false)
            .accountLocked(false)
            .roles(user.getRole().toString())
            .build();
        return details;
            
        
    }
    
}
