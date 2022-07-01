package com.dev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    @JsonIgnore
    private Long id;
    private String name;
    @JsonIgnore
    private Boolean status;
}
