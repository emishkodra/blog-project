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
    @NotNull
    private String title;
    private Instant createdOn = Instant.now();

    @NotNull
    private UserDTO userDTO;
}
