package com.rvs.hhsearch.controller;

import com.rvs.hhsearch.model.User;
import com.rvs.hhsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class AdminController {
//    private String lastLogin;
    @Qualifier("userServiceImp")
    @Autowired
    private UserService userService;
//    @Qualifier("roleServiceImp")
//    private final RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }

    @GetMapping("/admin/users")
    public ModelAndView getAllUsers(Authentication auth) {
        ModelAndView mv = new ModelAndView();
//            mv.addObject("users", userService.getAll());
        mv.addObject("authUser", (User) auth.getPrincipal());
        mv.addObject("userEmail",((User) auth.getPrincipal()).getLogin());
//            mv.addObject("user", new User()); //для Thymeleaf нужно передать объект
        mv.addObject("rolesAuth",((User) auth.getPrincipal()).getRoles()
                .stream().map(Objects::toString).collect(Collectors.joining(", ")));
        mv.setViewName("admin/users");
        return mv;
    }
}
