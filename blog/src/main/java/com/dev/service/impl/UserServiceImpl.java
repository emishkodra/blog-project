package com.dev.service.impl;

import com.dev.dao.RoleDAO;
import com.dev.dao.UserDAO;
import com.dev.dto.UserDTO;
import com.dev.model.Role;
import com.dev.model.User;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userListDTO.add(userDTO);
            }
        }
        return userListDTO;
    }

    @Override
    public User userUpdate(UserDTO userDTO, Long id) throws Exception {

        User updatedUser = null;
        try {
            if (userDTO != null && id > 0) {

                Optional<User> user = userDao.findById(id);
                Optional<Role> roleUser = roleDao.findRoleByUserId(id);

                if (user.isPresent()) {

                    User usr = user.get();
                    usr.setUsername(userDTO.getUsername());
                    usr.setEmail(userDTO.getEmail());
                    usr.setEnabled(true);

                    List<Role> authorities = new ArrayList<>();

                    if (roleUser.isPresent()) {
                        authorities.add(roleUser.get());
                    } else {
                        Role roleIsUser = this.roleDao.findByRoleName("ROLE_USER");
                        if (roleIsUser != null) {
                            authorities.add(roleIsUser);
                        }
                    }
                    usr.setAuthorities(authorities);
                    updatedUser = userDao.save(usr);
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return updatedUser;
    }

    @Override
    public User changePassword(Long id, String newPassword) throws Exception {
        User updatedPassword = null;
        try {
            if (id != null || id > 0) {
                Optional<User> user = userDao.findById(id);

                if (user.isPresent()){
                    User newPass = user.get();
                    newPass.setPassword(passwordEncoder.encode(newPassword));
                    updatedPassword = userDao.save(newPass);
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return updatedPassword;
    }

    @Override
    public Boolean deleteUsers(Long id) throws Exception {
        try {
            if (id != null){
                Optional<User> user = userDao.findById(id);

                if (user.isPresent()) {
                    User usr = user.get();
                    usr.setEnabled(false);
                    userDao.delete(usr);
                    return true;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
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
