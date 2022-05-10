package com.dev.dao;

import com.dev.model.Role;
import com.dev.dao.repository.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface RoleDAO extends SoftDeleteRepository<Role, Long> {

    @Query(value = "SELECT e.* FROM role e WHERE e.status is not false and e.name  = :role  ",
            nativeQuery = true
    )
    Role findByRoleName(@Param("role") String role);

    @Query(value = "select r.* from role r\n" +
            "inner join user_role usr on r.id=usr.role_id\n" +
            "inner join users u on u.id= usr.user_id\n" +
            "where u.enabled is not false\n " +
            "and usr.user_id=:id ",
            nativeQuery = true
    )
    Optional<Role> findRoleByUserId(@Param("id") Long id);



}
