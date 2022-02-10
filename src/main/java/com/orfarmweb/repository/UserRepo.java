package com.orfarmweb.repository;

import com.orfarmweb.constaint.Role;
import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("SELECT u from User u where u.email = :email")
    User findUserByEmail(@Param("email") String email);
    @Query(value = "SELECT email from User ", nativeQuery = true)
    List<String> getEmail();
    @Query(value = "update user set first_name = :firstName , last_name =:lastName where user.id = :id",nativeQuery = true)
    boolean saveUserByEmail(String firstName, String lastName, int id);
    @Query(value = "select count(*) from User where role = 1", nativeQuery = true)
    Integer countUserByRole();
    List<User> getUserByRole(Role role);
}
