//package com.dev.service;
//
//import com.dev.model.Role;
//import com.dev.model.User;
//import com.dev.repository.RoleRepository;
//import com.dev.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    public void registerDefaultUser(User user) {
//        Role roleUser = roleRepository.findByName("ROLE_USER");
//        user.addRole(roleUser);
//        userRepository.save(user);
//    }
//
//    public List<User> listAll() {
//        return userRepository.findAll();
//    }
//}
