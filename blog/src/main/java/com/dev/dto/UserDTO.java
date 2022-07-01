package com.dev.dto;

import com.dev.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class UserDTO {

    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private List<RoleDTO> authorities;

}

