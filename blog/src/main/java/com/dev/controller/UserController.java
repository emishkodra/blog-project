package com.dev.controller;

import com.dev.dao.RoleDAO;
import com.dev.dao.UserDAO;
import com.dev.dto.UserDTO;
import com.dev.model.Role;
import com.dev.model.User;
import com.dev.security.JwtTokenUtil;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDAO userDao;
    @Autowired
    private RoleDAO roleDao;

    @GetMapping(value = "/user/all")
    public ResponseEntity<List<UserDTO>> allActiveUsers(@PathVariable Long userId) {
        List<UserDTO> response = null;
        try {
            response = userService.userActiveListDetails();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/getUserId")
    public ResponseEntity<Integer> getUserId(@RequestBody String token) {
        Integer response = null;
        try {
            response = (Integer) jwtUtil.getIdFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/getUser")
    public ResponseEntity<User> getUser(@RequestBody Long id) {
        User response = null;
        try {
            response = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/delete")
    public ResponseEntity<Boolean> deleteEmployee(@RequestBody UserDTO userDTO) {
        Boolean response = Boolean.FALSE;
        try {
            if (userDTO != null) {
                response = userService.deleteUserActiveDetails(userDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/users/adduser")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDto, String role) {
        User response = new User();
        try {
//            if (user != null && user.getUsername().getId() > 0) {

//                User u = userService.getUserById(user.getUsername().getId());

                User user = new User();
//                if (u != null) {
                    user.setUsername(userDto.getUsername());
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    user.setUsername(userDto.getUsername());
                    user.setEnabled(true);
                    Role roleUser = null;

                    if (role != null) {
                        roleUser = this.roleDao.findByRoleName(role);
                    } else {
                        roleUser = this.roleDao.findByRoleName("ROLE_USER");
                    }

                    List<Role> authorities = new ArrayList<>();
                    if (roleUser != null)
                        authorities.add(roleUser);

                    user.setAuthorities(authorities);
                    response = userDao.save(user);
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users/role")
    public ResponseEntity<List<String>> getActiveRoles() {
        List<String> response = null;
        try {
            response = this.userService.getUserActiveRoles();
            response.remove("ROLE_SUPERADMIN");
            response.remove("ROLE_MANAGER");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }


}
