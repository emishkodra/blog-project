package com.dev.mapper;

import com.dev.dto.UserDTO;
import com.dev.model.User;
import com.dev.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(source="authorities",target = "authorities")
    UserDTO toDto(User user);

    @Mapping(source="authorities",target = "authorities")
    User toEntity(UserDTO userDTO);

}
