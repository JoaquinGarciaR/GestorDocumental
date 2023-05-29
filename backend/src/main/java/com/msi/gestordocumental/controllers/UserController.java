package com.msi.gestordocumental.controllers;

import com.msi.gestordocumental.exceptions.BadRequestException;
import com.msi.gestordocumental.entities.User;
import com.msi.gestordocumental.exceptions.UnAuthorizedException;
import com.msi.gestordocumental.services.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping(value = "/api/v1/user")
@RestController
public class UserController {
        @Autowired
        private UserService service;

        @GetMapping
        public List<User> findAllUsers(@RequestParam("user_id") String id){
            List<User> usersinAutor = service.getAllUsers();
            User autor = service.getUser(id);
            usersinAutor.remove(autor);
            return usersinAutor;
        }

        @GetMapping(path = "{id}")
        public User findUserById(@PathVariable("id") String id) {
            return service.getUser(id);
        }

        @PostMapping(path = "/login")
        public User loginUser(@RequestBody User user){
            if(user.getEmail().isEmpty() || user.getPasswordEncrypt().isEmpty())
                throw  new BadRequestException();
            User loggedUser = service.loginUser(user.getEmail(), user.getPasswordEncrypt());
            if(loggedUser == null)
                throw new UnAuthorizedException();
            User userReturn = new User();
            userReturn.setIdUser(loggedUser.getIdUser());
            userReturn.setRole(loggedUser.getRole());
            userReturn.setDepartament(loggedUser.getDepartament());
            userReturn.setUnit(loggedUser.getUnit());
            return userReturn;
        } 

        @PostMapping(path = "/register")
        public Integer addUser(@RequestBody User user){
            if(validateUser(user.getEmail())){ //
                throw new ResponseStatusException(HttpStatus.SC_BAD_REQUEST, "Email already exists.\n", null);
            }
            if(service.getUser(user.getIdUser())!= null){
                throw new ResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "User already exists.\n", null);
            }
            else {
                if (isValidPassword(user.getPasswordEncrypt())) {
                    service.saveUser(user); //Password is correct
                    throw new ResponseStatusException(HttpStatus.SC_OK, "Registration correct.\n", null);
                } else {
                    throw new ResponseStatusException(HttpStatus.SC_FORBIDDEN, "The password is invalid.\n", null);
                }
            }
        }
    
       public Boolean validateUser(String email){
            if(service.getUserByEmail(email) != null ){
                return true;
            }
            else
                return false;
       }

       public boolean isValidPassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); // if the pwd is valid then true 
        // Must have at least one numeric character
        // Must have at least one lowercase character
        // Must have at least one uppercase character
        // Must have at least one special symbol among @#$%
        // Password length should be between 8 and 20
    }
}
