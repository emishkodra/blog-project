package com.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1,initialValue = 1)
    @JsonIgnore
    private Long id;

    @Column(name = "NAME", length = 50)
    @NotNull
    private String name;
    private Boolean status;
}