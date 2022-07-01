package com.dev.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@ToString

public class PostDTO {

    private Long id;
    @NotNull
    private String content;
    private String title;
    private Instant createdOn;

    @NotNull
    private UserDTO userDTO;
}
