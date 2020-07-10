package com.rvs.hhsearch.controller;

import com.rvs.hhsearch.dto.UserDTO;
import com.rvs.hhsearch.model.Role;
import com.rvs.hhsearch.model.User;
import com.rvs.hhsearch.service.RoleService;
import com.rvs.hhsearch.service.UserService;
import com.rvs.hhsearch.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
    @Autowired
    private UserService us;
    @Autowired
    private RoleService rs;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/secret")
    public String hello(){

        if (us.isExistLogin("zzz")) {
            return "hello";
        }


        User user = new User();
        user.setName("First");
        user.setSurname("Admin");
        user.setLogin("zzz");
        user.setPassword("zzz");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(rs.getRoleByName("ADMIN"));
        roleSet.add(rs.getRoleByName("USER"));
        user.setRoles(roleSet);
        UserDTO userDTO = new UserDTO(user);

        us.save(userDTO);

        return "hello";
    }

}
