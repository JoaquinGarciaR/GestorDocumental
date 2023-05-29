package com.msi.gestordocumental.repos;

import com.msi.gestordocumental.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM [User] u WHERE u.email = ?1 AND u.password_encrypt = ?2",
            nativeQuery = true)
    User getUserByIdPassword(String email, String passwordEncrypt);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User getUserByEmail(String email);

    @Query(value ="SELECT departament_id FROM [user] u WHERE u.id_user = ?1",nativeQuery = true)
    Integer getUserDepartment(String id);

    @Query(value ="SELECT unit_id FROM [user] u WHERE u.id_user = ?1",nativeQuery = true)
    Integer getUserUnit(String id);


    @Query(value = "SELECT id_user FROM [user] u WHERE u.departament_id = ?1", nativeQuery = true)
    public List<String> getUsersbyDepartment(Integer id);

    @Query(value = "SELECT id_user FROM [user] u WHERE u.unit_id = ?1", nativeQuery = true)
    public List<String> getUsersbyUnit(Integer id);
}

