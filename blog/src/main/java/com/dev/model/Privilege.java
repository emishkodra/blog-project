//package com.dev.model;
//
//import javax.persistence.*;
//import java.util.Collection;
//
//public class Privilege {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private String name;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<Role> roles;
//}
