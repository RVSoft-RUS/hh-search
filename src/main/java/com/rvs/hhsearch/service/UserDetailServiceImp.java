package com.rvs.hhsearch.service;

import com.rvs.hhsearch.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailServiceImp implements UserDetailsService {
    private UserService userService;

    @Autowired
    public UserDetailServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(login);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("This user not found");
        }
    }
}
