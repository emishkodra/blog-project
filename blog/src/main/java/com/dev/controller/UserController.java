package com.dev.controller;

import com.dev.dao.RoleDAO;
import com.dev.dao.UserDAO;
import com.dev.dto.UserDTO;
import com.dev.model.User;
import com.dev.repository.UserRepository;
import com.dev.security.JwtTokenUtil;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
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
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users/all")
    public ResponseEntity<List<UserDTO>> allActiveUsers() {
        List<UserDTO> response = null;
        try {
            response = userService.userActiveListDetails();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/users/getUserId")
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

    @PostMapping("/users/getUser")
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

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/users/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        User response;
        try {
            if (userDTO == null && id == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                response = userService.userUpdate(userDTO, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/users/changePassword/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestParam String newPassword){
        User response;
        try {
            if (id == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                response = userService.changePassword(id, newPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<Boolean> deleteUser(@RequestBody UserDTO userDTO) {
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
