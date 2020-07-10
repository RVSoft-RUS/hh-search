package com.rvs.hhsearch;

import com.rvs.hhsearch.model.Role;
import com.rvs.hhsearch.model.User;
import com.rvs.hhsearch.repository.UserRepository;
import com.rvs.hhsearch.service.RoleServiceImp;
import com.rvs.hhsearch.service.UserServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class HhSearchApplication {
    @Bean
    public BCryptPasswordEncoder getPassEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(HhSearchApplication.class, args);

    }

}
