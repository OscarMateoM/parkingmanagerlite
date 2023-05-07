package com.hormigo.david.parkingmanager.core.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        // TODO: Cambiarlo para meterlo en la BBDD
        BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
        String password = pass.encode(user.getPassword());
        UserDetails details = User.withUsername(user.getEmail())
            .password(password)
            .accountExpired(false)
            .accountLocked(false)
            .roles("USER")
            .build();
        return details;
            
        
    }
    
}
