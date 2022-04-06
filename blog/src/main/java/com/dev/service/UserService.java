package com.dev.service;

import com.dev.dto.UserDTO;
import com.dev.model.User;

import java.util.List;

public interface UserService {

    List<UserDTO> userActiveListDetails() throws Exception;

    User getUserById(Long id) throws Exception;

    Boolean deleteUserActiveDetails(UserDTO userDTO) throws Exception;

    List<String> getUserActiveRoles() throws Exception;

}
