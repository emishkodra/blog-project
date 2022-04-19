package com.dev.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("admin")
public class MethodProtectedRestController {

    public Boolean admin = false;
    public Boolean superadmin = false;
    public Boolean user = false;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<SimpleGrantedAuthority> hasRole() {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities;
    }

}
