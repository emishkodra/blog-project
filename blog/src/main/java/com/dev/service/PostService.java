package com.dev.service;

import com.dev.dto.PostDTO;
import com.dev.model.Image;
import com.dev.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostDTO> getAllPosts();

    void createNewPost(PostDTO postDTO, MultipartFile[] files) throws IOException;

    List<PostDTO> getPostForUserId(Long id);

    List<Image> createImageList(MultipartFile[] files, Post newPost) throws IOException;

    boolean deletePost(Long id);

    Optional<PostDTO> getPostWithId(Long postId);
}
