package com.dev.dao;

import com.dev.model.Role;
import com.dev.dao.repository.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleDAO extends SoftDeleteRepository<Role, Long> {
    @Query(value = "SELECT e.* FROM role e WHERE e.status is not false",
            nativeQuery = true
    )
    List<Role> findAllActiveRoles(@Param("cid") Long cid);

    @Query(value = "SELECT e.* FROM role e WHERE e.status =false",
            nativeQuery = true
    )
    List<Role> findAllDeletedRoles(@Param("cid") Long cid);

    @Query(value = "SELECT e.* FROM role e WHERE e.status is not false and e.name  = :role  ",
            nativeQuery = true
    )
    Role findByRoleName(@Param("role") String role);

    @Query(value = "select r.* from role r\n" +
            "inner join user_role usr on r.id=usr.role_id\n" +
            "inner join users_ u on u.id= usr.user_id\n" +
            "inner join employee e on e.id= u.employee_id\n" +
            "where r.status is not false\n" +
            "and u.enabled is not false\n " +
            "and u.status is not false \n " +
            "and e.status is not false \n" +
            "and e.id=:id ",
            nativeQuery = true
    )
    Optional<Role> findRoleByEmployeeId(@Param("id") Long id);

    @Modifying
    @Query(value = "update user_role set role_id=:roleId where user_id=:userId \n", nativeQuery = true)
    int updateOldRoleWithNewRoleInUserRole(@Param("roleId") Long roleId,@Param("userId") Long userId);

}
