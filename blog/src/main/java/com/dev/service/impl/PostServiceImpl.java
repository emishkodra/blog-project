package com.dev.service.impl;

import com.dev.dao.PostDAO;
import com.dev.dao.UserDAO;
import com.dev.dto.PostDTO;
import com.dev.dto.UserDTO;
import com.dev.mapper.PostMapper;
import com.dev.mapper.UserMapper;
import com.dev.model.Image;
import com.dev.model.Post;
import com.dev.model.User;
import com.dev.service.PostService;
import com.dev.util.error.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.dev.util.AppConstants.LIKE_NUMBER_0;
import static com.dev.util.error.ErrorConstants.*;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
@Transactional
@Component
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDAO postDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PostMapper postMapper;


    @Override
    public void createNewPost(PostDTO postDTO, MultipartFile[] files) throws IOException {

        Post post = new Post();

        Optional<User> user = userDAO.findById(postDTO.getUserDTO().getId());
        if (user.isPresent()) {

            post.setUser(user.get());
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setCreatedOn(Instant.now());
            post.setStatus(true);
//            post.setImages(createImageList(files, post));
            post.setLikes(LIKE_NUMBER_0);

            postDAO.save(post);

        } else {
            throw new BadRequestAlertException(USER_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
    }

    @Override
    public List<Image> createImageList(MultipartFile[] files, Post post) throws IOException {

        List<Image> imageList = new ArrayList<>();

        for (MultipartFile file : files) {
            Image image = new Image();
            image.setFileContent(compressBytes(file.getBytes()));
            image.setFileName(file.getOriginalFilename());
            image.setPost(post);
            imageList.add(image);
        }
        return imageList;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return  postDAO.findAllByOrderByIdDesc().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public boolean deletePost(Long postId) {
        if (postId != null) {
            Optional<Post> post = postDAO.findById(postId);

            if (post.isPresent()) {
                Post userPost = post.get();
                userPost.setStatus(false);
                postDAO.delete(userPost);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<PostDTO> getPostWithId(Long postId) {

        Optional<PostDTO> post =
                postDAO.findById(postId)
                .map(postMapper::toDto);

        if (post.isPresent()) {
            return post;
        } else {
            throw new BadRequestAlertException(POST_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
    }

    @Override
    public List<PostDTO> getPostForUserId(Long userId) {

//        List<PostDTO> postDTOList = new ArrayList<>();
        Optional<User> user = userDAO.findById(userId);

        if (!user.isPresent()) {
            throw new BadRequestAlertException(CONFLICT_ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }

        return postDAO.findPostByUserId(userId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));

//        List<Post> posts = postDAO.findPostByUserId(userId);
//        for (Post post : posts) {
//            PostDTO postDTO = new PostDTO();
//
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(post.getUser().getId());
//            userDTO.setUsername(post.getUser().getUsername());
//
//            postDTO.setUserDTO(userDTO);
//            postDTO.setId(post.getId());
//            postDTO.setContent(post.getContent());
//            postDTO.setTitle(post.getTitle());
//            postDTO.setCreatedOn(post.getCreatedOn());
//
//
//            postDTOList.add(postDTO);
//        }
//
//        return postDTOList;
    }
}
