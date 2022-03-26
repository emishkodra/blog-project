package com.dev.repository;

import com.dev.model.Role;
import com.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<User, Long> {
    Role findByName(String user);

}
