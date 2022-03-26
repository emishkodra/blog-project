//package com.dev.service.impl;
//
//import com.dev.model.User;
//import com.dev.repository.UserRepository;
//import com.dev.security.JwtUserFactory;
//import com.dev.service.UserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@Service
//public class UserDetailServiceImpl{
//    @Autowired
//    private UserRepository userRepository;
//
//    //    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
////        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username Not Found" + username));
////
////        // what authority is this user having
////        return new org.springframework.security.core.userdetails.User(user.getUsername(),
////                user.getPassword(), true, true, true, true,
////                getAuthorities("ROLE_USER")); // user authority
////
////    }
//    public UserDetails loadUserByUsername1(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
////read emplyee
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
//        } else {
//            return JwtUserFactory.create(user);
//        }
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
//        return Collections.singleton(new SimpleGrantedAuthority(role_user)); //custom user detailed service
//    }
//
//}
