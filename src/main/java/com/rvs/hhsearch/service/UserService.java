package com.rvs.hhsearch.service;

import com.rvs.hhsearch.dto.UserDTO;
import com.rvs.hhsearch.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> getAll();
    UserDTO getById(long id);
    User getUserByLogin(String login);
    boolean save(UserDTO userDTO);
    boolean deleteById(long id);
    boolean isExistLogin(String login);
}
