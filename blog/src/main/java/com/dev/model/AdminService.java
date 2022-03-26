package com.dev.model;

import org.springframework.security.access.prepost.PreAuthorize;

public class AdminService{
    @PreAuthorize("hasRole(@roles.ADMIN)")
    public boolean ensureAdmin() {
        return true;

    }
}
