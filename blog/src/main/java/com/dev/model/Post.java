package com.dev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;


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
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
