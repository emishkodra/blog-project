package com.dev.dao;

import com.dev.dao.repository.SoftDeleteRepository;
import com.dev.model.Post;
import com.dev.model.User;

import java.util.List;

public interface PostDAO extends SoftDeleteRepository<Post, Long> {

    List<Post> findPostByUserId(Long id);

    List<Post> findAllByOrderByIdDesc();




}
