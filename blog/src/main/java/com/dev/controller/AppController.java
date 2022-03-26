//package com.dev.controller;
//
//import com.dev.model.User;
//import com.dev.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//public class AppController {
//
//    @Autowired
//    private UserService service;
//
//    @PostMapping("/register")
//    public String processRegister(User user) {
//        service.registerDefaultUser(user);
//        return "register_success";
//    }
//
//    @GetMapping("/users")
//    public String listUsers(Model model) {
//        List<User> listUsers = service.listAll();
//        model.addAttribute("listUsers", listUsers);
//        return "users";
//    }
//}

