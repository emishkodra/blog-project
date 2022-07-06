package com.dev.service.impl;

import com.dev.dao.RoleDAO;
import com.dev.dao.UserDAO;
import com.dev.dto.UserDTO;
import com.dev.mapper.RoleMapper;
import com.dev.mapper.UserMapper;
import com.dev.model.Role;
import com.dev.model.User;
import com.dev.service.UserService;
import com.dev.util.error.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dev.util.error.ErrorConstants.NOT_FOUND_KEY;
import static com.dev.util.error.ErrorConstants.USER_NOT_FOUND_MESSAGE;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;
    @Autowired
    private RoleDAO roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> userActiveListDetails(){
        return userDao.findAllByOrderByIdDesc().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<UserDTO> getUserWithId(Long id){
        Optional<UserDTO> user = userDao.findById(id)
                .map(userMapper::toDto);

        if (user.isPresent()){
            return user;
        } else {
            throw new BadRequestAlertException(USER_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
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
    public Boolean deleteUsers(Long id){
            if (id != null) {
                Optional<User> user = userDao.findById(id);

                if (user.isPresent()) {
                    User usr = user.get();
                    usr.setEnabled(false);
                    userDao.delete(usr);
                    return true;
                }
            }
            return false;
    }

    @Override
    public List<String> getUserActiveRoles() {
        return roleDao.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
