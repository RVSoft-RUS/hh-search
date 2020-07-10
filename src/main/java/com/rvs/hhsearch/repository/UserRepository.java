package com.rvs.hhsearch.repository;

import com.rvs.hhsearch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUsersByLogin(String login);
}
