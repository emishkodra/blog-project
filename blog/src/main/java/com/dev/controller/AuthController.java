package com.dev.controller;

import com.dev.dto.LoginRequestDTO;
import com.dev.dto.RegisterRequestDTO;
import com.dev.repository.UserRepository;
import com.dev.service.AuthService;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController { //method that will be invoked whenever a POST request is made (register user)

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequestDTO registerRequest) { //accessing DTO
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
        }
        authService.signup(registerRequest);
        return new ResponseEntity("User registered successfully", HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest)  throws Exception{
        try {
            return authService.login(loginRequest); // return to client
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
