package com.dev.controller;

import com.dev.dto.PostDTO;
import com.dev.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    // create post
    @PostMapping("/add")
    public ResponseEntity createPost(@RequestBody PostDTO postDTO){
        postService.createPost(postDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // get post
    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> showAllPosts(){
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    // get single post
    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDTO> getSinglPost(@PathVariable @RequestBody Long id){
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }
}
