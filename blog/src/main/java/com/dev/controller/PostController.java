package com.dev.controller;

import com.dev.dao.UserDAO;
import com.dev.dto.PostDTO;
import com.dev.model.Post;
import com.dev.model.User;
import com.dev.service.PostService;
import com.dev.service.UserService;
import com.dev.util.error.BadRequestAlertException;
import com.dev.util.error.HeaderUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dev.util.AppConstants.ALLOWED_FILE_TYPES_ARRAY;
import static com.dev.util.error.ErrorConstants.*;


@RestController
@RequestMapping("/api/auth")
public class PostController {

    private static final String ENTITY_NAME = "post";

    @Autowired
    private PostService postService;


    @GetMapping(value = "/posts/all")
    public List<PostDTO> allActivePosts() {
        return postService.getAllPosts();
    }

    @GetMapping(value = "/posts/{postId}")
    public Optional<PostDTO> getPostWithId(@PathVariable Long postId) {

        if (postId == null || postId == 0) {
            throw new BadRequestAlertException(USER_POST_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        return postService.getPostWithId(postId);
    }

    @GetMapping(value = "/posts/users/{userId}")
    private List<PostDTO> getPostForUserId(@PathVariable Long userId) {
        List<PostDTO> userPostList;

        if (userId == null) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        userPostList = postService.getPostForUserId(userId);

        if (userPostList.isEmpty()) {
            throw new BadRequestAlertException(USER_POST_NOT_FOUND_MESSAGE, ENTITY_NAME, NO_CONTENT_KEY);
        }
        return userPostList;
    }

    @PostMapping(value = "/posts/newPost")
    public ResponseEntity<PostDTO> newPost(@Valid @RequestBody PostDTO postDTO){

//        for (MultipartFile multipartFile : files) {
//            if (!ALLOWED_FILE_TYPES_ARRAY.contains(Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())))) {
//                throw new BadRequestAlertException(ALLOWED_FILE_TYPES_ERROR_MESSAGE, ENTITY_NAME, ALLOWED_FILE_TYPES_ERROR_KEY);
//            }
//        }

        postService.createNewPost(postDTO);

        return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, postDTO.toString()))
                    .build();
    }


    @DeleteMapping(value = "/posts/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        if (postId == null || postId == 0){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        Boolean deletedPost = postService.deletePost(postId);
        if (deletedPost){
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, postId.toString()))
                    .build();
        } else {
            throw new BadRequestAlertException(ID_DELETED_OR_NOT_FOUND_MESSAGE,ENTITY_NAME, NOT_FOUND_KEY);
        }
    }


}