package com.Malik.RentO.Service;

import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Attempting to load user with email: " + email);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("User found: " + user.getEmail() + ", Password: " + user.getPassword());
        System.out.println("User roles: " + user.getUserRoles());

        // Handle null userRoles
        String[] roles = user.getUserRoles() != null ? 
            user.getUserRoles().toArray(new String[0]) : 
            new String[]{"USER"};

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
