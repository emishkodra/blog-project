package com.dev.controller;

import com.dev.dto.LoginRequestDTO;
import com.dev.dto.RegisterRequestDTO;
import com.dev.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController { //method that will be invoked whenever a POST request is made (register user)

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequestDTO registerRequest) { //accessing DTO
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?>  login(@RequestBody LoginRequestDTO loginRequest)  throws Exception{
        try {
            return authService.login(loginRequest); // return to client

        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
