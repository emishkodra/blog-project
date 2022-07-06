package com.dev.service;

import com.dev.dto.UserDTO;
import com.dev.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> userActiveListDetails();

    Optional<UserDTO> getUserWithId(Long id);

    User userUpdate(UserDTO userDTO, Long id) throws Exception;

    User changePassword(Long id, String newPassword) throws Exception;

    Boolean deleteUsers(Long id);

    List<String> getUserActiveRoles() throws Exception;
}
