package com.dev.dao;

import com.dev.model.User;
import com.dev.dao.repository.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends SoftDeleteRepository<User, Long> {
    @Query(value = "select u.* from user_ u \n" +
//            "inner join employee e on u.employee_id = e.id \n" +
            "WHERE u.enabled is not false\n" +
            "and u.status is not false\n",
//            "and e.status is not false\n" +
//            "and u.company_id = :cid",
            nativeQuery = true
    )
    List<User> findAllActiveUsers(@Param("cid") Long cid);

    @Query(value = "SELECT p.* FROM user_ p WHERE p.enabled = false  ", // and p.company_id = :cid
            nativeQuery = true
    )
    List<User> findAllInActiveUsers(@Param("cid") Long cid);
//
//    @Query(value = "select u.* from users_ u \n" +
//            "inner join employee e on u.employee_id = e.id \n" +
//            "WHERE u.enabled is not false\n" +
//            "and u.status is not false\n" +
//            "and e.status is not false\n" +
//            "and u.company_id = :cid\n" +
//            "and u.employee_id=:empId ",
//            nativeQuery = true
//    )
//    User findUsersByEmployee(@Param("empId") Long empId, @Param("cid") Long cid);
//
//    @Query(value = " select count(u.id) from users_ u \n" +
//            "inner join employee e on u.employee_id = e.id \n" +
//            "WHERE u.enabled = true\n" +
//            "and u.status is not false\n" +
//            "and e.status is not false\n" +
//            "and username = :username",
//            nativeQuery = true
//    )
//
//    int findIfUserExistsOnOtherCompany (@Param("username") String username);
//
//    @Query(value = "select u.password from users_ u \n" +
//            "inner join employee e on u.employee_id = e.id \n" +
//            "WHERE u.enabled = true\n" +
//            "and u.status is not false\n" +
//            "and e.status is not false\n" +
//            "and u.username = :username",
//            nativeQuery = true
//    )
//    List<String> findOtherPasswordsOfOneUser (@Param("username") String username);
//
//
//    @Query(value = "SELECT COUNT(*) FROM  users_ u \n" +
//            "inner join employee e on u.employee_id = e.id \n" +
//            "where u.status is not false\n" +
//            "and e.status is not false\n" +
//            "and u.username = :username  \n" +
//            "and u.company_id = :cid",
//            nativeQuery = true
//    )
//    Object isUser(@Param("username") String username, @Param("cid") Long cid);
//
//
//    @Query(value = "SELECT u.* FROM   users_ u \n" +
//            "inner join employee e on u.employee_id = e.id \n" +
//            "where u.enabled is not false \n" +
//            "and u.status is not false\n" +
//            "and e.status is not false\n" +
//            "AND u.username = :username  \n" +
//            "and u.company_id = :cid",
//            nativeQuery = true)
//    User UserByUsername(@Param("username") String username, @Param("cid") Long cid);
//
//    @Query(value = "SELECT u.* FROM users_ u \n" +
//            "inner join employee e on u.employee_id = e.id \n" +
//            "WHERE u.enabled is not false \n" +
//            "and u.status is not false\n" +
//            "and e.status is not false\n" +
//            "and u.company_id = :cid ",
//            nativeQuery = true)
//    User findUserByCompany(@Param("cid") Long cid);
//
//
//    Optional<User> findUsersByEmployeeId(@Param("id") Long Id);

}
