package com.dev.dao;

import com.dev.model.User;
import com.dev.dao.repository.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends SoftDeleteRepository<User, Long> {
    @Query(value = "select u.* from users u \n" +
            "WHERE u.enabled is not false\n" ,
            nativeQuery = true
    )
    List<User> findAllActiveUsers();

    List<User> findAllByOrderByIdDesc();


}
