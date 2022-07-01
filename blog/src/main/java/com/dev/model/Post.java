package com.dev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "POST")
@Getter
@Setter

public class Post {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Instant createdOn;
    private Long likes;

    @NotNull
    private Boolean status = true;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Image> images;
}
