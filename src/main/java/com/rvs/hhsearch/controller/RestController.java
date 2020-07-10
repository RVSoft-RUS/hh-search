package com.rvs.hhsearch.controller;

import com.rvs.hhsearch.model.User;
import com.rvs.hhsearch.service.RoleService;
import com.rvs.hhsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.rvs.hhsearch.dto.UserDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Qualifier("userServiceImp")
    private final UserService userService;
    @Qualifier("roleServiceImp")
    private final RoleService roleService;

    @Autowired
    public RestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> read() {
        List<UserDTO> userDTOList = userService.getAll();

        return userDTOList.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> read(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.getById(id);

        return userDTO == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity<UserDTO> getUserAuth(Authentication auth) {
        UserDTO userDTO = new UserDTO((User) auth.getPrincipal());

        return userDTO == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        if (isCorrect(userDTO)) {
            userService.save(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
        if (!isCorrect(userDTO)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return userService.save(userDTO)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.deleteById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    private boolean isCorrect(UserDTO userDto) {
        if(userDto.getName().equals("")
                || userDto.getSurName().equals("")
                || userDto.getLogin().equals("")
                || userDto.getPassword().equals("")
                || userDto.getRoles().length == 0) {
            return false;
        }
        return true;
    }
}
