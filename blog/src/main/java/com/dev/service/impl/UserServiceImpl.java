package com.dev.service.impl;

import com.dev.dao.RoleDAO;
import com.dev.dao.UserDAO;
import com.dev.dto.UserDTO;
import com.dev.model.Role;
import com.dev.model.User;
import com.dev.security.JwtUser;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;
    @Autowired
    private RoleDAO roleDao;

    @Override
    public List<UserDTO> userActiveListDetails() throws Exception {
        List<User> userList = null;
        List<UserDTO> userListDTO = new ArrayList<>();
        try {
            userList = userDao.findAllByOrderByIdDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(userList != null) {
            for(User user : userList) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userListDTO.add(userDTO);
            }
        }
        return userListDTO;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        try {
            Optional<User> user = userDao.findById(id);
            return user.orElse(null);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public Boolean deleteUserActiveDetails(UserDTO userDTO) throws Exception {
        Boolean flag = Boolean.FALSE;
        try {
            if (userDTO != null) {
//                Optional<User> user = this.userDao.findById(userDTO.getId());// empDTO.getEmployeeFromDto(empdto);
//                flag = this.deleteEmployee(user.get());
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return flag;
    }

    @Override
    public List<String> getUserActiveRoles() throws Exception {
        List<String> roleTypes = null;
        try {
            roleTypes = roleDao.findAll().stream().map(Role::getName).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return roleTypes;
    }
}
