package com.dev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "IMAGE")


public class Image {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] fileContent;

    private String fileName;
    private Boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
