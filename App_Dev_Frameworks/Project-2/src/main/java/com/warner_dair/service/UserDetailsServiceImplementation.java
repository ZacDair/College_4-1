package com.warner_dair.service;

import com.warner_dair.dao.UserDao;
import com.warner_dair.entities.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomUser> optionalUser = userDao.findById(username);
        if (optionalUser.isPresent()){
            CustomUser user = optionalUser.get();
            return User.builder()
                    .username(user.getUserEmail())
                    .password(user.getUserPassword())
                    .roles(user.getUserRole())
                    .build();
        }
        else{
            throw new UsernameNotFoundException("User Email: " + username + " was not found");
        }
    }
}
