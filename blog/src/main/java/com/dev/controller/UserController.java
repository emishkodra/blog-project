package com.dev.controller;

import com.dev.dto.PostDTO;
import com.dev.dto.UserDTO;
import com.dev.model.User;
import com.dev.security.JwtTokenUtil;
import com.dev.service.UserService;
import com.dev.util.error.BadRequestAlertException;
import com.dev.util.error.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.dev.util.error.ErrorConstants.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private static final String ENTITY_NAME = "user";

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @GetMapping(value = "/users/all")
    public List<UserDTO> allActiveUsers() {
        return userService.userActiveListDetails();
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
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
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            if (id == null || id == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
               userService.changePassword(id, newPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Password successfully changed", HttpStatus.OK);
    }

    //    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (id == null || id == 0) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }
        Boolean isDeleted = userService.deleteUsers(id);

        if (isDeleted) {
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                    .build();
        } else {
            throw new BadRequestAlertException(ID_DELETED_OR_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
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

    @GetMapping(value = "/users/{id}")
    public Optional<UserDTO> getPostWithId(@PathVariable Long id){
        if (id == null || id == 0){
            throw new BadRequestAlertException(USER_POST_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        return userService.getUserWithId(id);
    }
}
