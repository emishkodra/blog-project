package com.dev.service;

import com.dev.dto.UserDTO;
import com.dev.model.User;

import java.util.List;

public interface UserService {

    List<UserDTO> userActiveListDetails() throws Exception;

    User getUserById(Long id) throws Exception;

    User userUpdate(UserDTO userDTO, Long id) throws Exception;

    User changePassword(Long id, String newPassword) throws Exception;

    Boolean deleteUsers(Long id) throws Exception;

    List<String> getUserActiveRoles() throws Exception;
}
