package com.msi.gestordocumental.services;

import com.msi.gestordocumental.entities.User;
import com.msi.gestordocumental.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User saveUser(User obj){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(obj.getPasswordEncrypt());
        obj.setPasswordEncrypt(hashedPassword);
        return repository.save(obj);
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User getUser(String id){
        return repository.findById(id).orElse(null);
    }

    public User loginUser(String email, String passwordEncrypt) {
        User userFound = repository.getUserByEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordMatched = passwordEncoder.matches(passwordEncrypt, userFound.getPasswordEncrypt());
        if (passwordMatched) {
            return userFound;
        } else {
            return null;
        }
    }

    public User getUserByEmail(String email){
        return repository.getUserByEmail(email);
    }

    public Integer getUserDepartment(String id){
        return repository.getUserDepartment(id);
    }

    public Integer getUserUnit(String id){
        return repository.getUserUnit(id);
    }

    public List<String> getAllUsersbyDepartment(Integer id){
        return repository.getUsersbyDepartment(id);
    }

    public List<String> getAllUsersbyUnit(Integer id){
        return repository.getUsersbyUnit(id);
    }
}