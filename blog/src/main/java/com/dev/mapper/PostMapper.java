package com.dev.mapper;



import com.dev.dto.PostDTO;
import com.dev.model.Post;
import com.dev.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source="user",target = "userDTO")
    PostDTO toDto(Post post);

    @Mapping(source="userDTO",target = "user")
    Post toEntity(PostDTO postDTO);

}
