package com.dev.repository;

import com.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername (String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail (String email);
}
