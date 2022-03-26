package com.dev.service;

import com.dev.dto.PostDTO;
import com.dev.exception.PostNotFoundException;
import com.dev.model.Post;
import com.dev.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    public List<PostDTO> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostDTO postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDTO readSinglePost(Long id) {
        Post post = null;
        try {
            post = (Post) postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return mapFromPostToDto(post);
    }

    private PostDTO mapFromPostToDto(Post post) {
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        User loggedInUser = null;
        try {
            loggedInUser = (User) authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        return post;
    }
}
