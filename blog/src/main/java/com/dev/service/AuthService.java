package com.dev.service;

import com.dev.dto.LoginRequestDTO;
import com.dev.dto.RegisterRequestDTO;
import com.dev.model.User;
import com.dev.repository.UserRepository;
import com.dev.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MethodProtectedRestController methodProtectedRestController;
    // registration handler
    public void signup(RegisterRequestDTO registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(true);
        user.setPassword(encodePassword(registerRequest.getPassword()));
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password); // encrypted password
    }

    public ResponseEntity<?>  login(LoginRequestDTO loginRequest) throws Exception{

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (userDetails != null) {
            final String token = jwtTokenUtil.generateToken(userDetails);
            final Collection<SimpleGrantedAuthority> roles_array = methodProtectedRestController.hasRol();
            final String userId = jwtTokenUtil.getUserId();
            return ResponseEntity.ok(new JwtAuthenticationResponse(token, roles_array, userId));
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    public <T> Optional getCurrentUser() {
        // returning user from security context
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext()
                                     .getAuthentication()
                                     .getPrincipal();
        return Optional.of(principal); // store it inside a variable ==> return it back as optional
    }
}
