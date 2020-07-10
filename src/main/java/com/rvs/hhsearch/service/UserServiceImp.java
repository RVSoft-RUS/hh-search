package com.rvs.hhsearch.service;

import com.rvs.hhsearch.dto.UserDTO;
import com.rvs.hhsearch.model.User;
import com.rvs.hhsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDtoList = userList
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDTO getById(long id) {
        User user = userRepository.getOne(id);
        return new UserDTO(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUsersByLogin(login);
    }

    @Override
    public boolean save(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setRoles(Arrays.stream(userDTO.getRoles()).map(roleService::getRoleByName).collect(Collectors.toSet()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteById(long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean isExistLogin(String login) {
        User user = userRepository.getUsersByLogin(login);
        return (user != null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUserByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
