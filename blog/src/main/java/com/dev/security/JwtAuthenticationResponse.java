package com.dev.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;
    private final Collection<SimpleGrantedAuthority> collection;
    private final String token;
    private String userId;

    public JwtAuthenticationResponse(String token, Collection<SimpleGrantedAuthority> collection, String userId) {
        this.token = token;
        this.collection = collection;
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }

    public Collection<SimpleGrantedAuthority> getCollection() {
        return collection;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
