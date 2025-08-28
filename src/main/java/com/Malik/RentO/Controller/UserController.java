package com.Malik.RentO.Controller;

import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        userService.registerUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{phone}")
    public ResponseEntity<?> getUserByPhone(@PathVariable Long phone){
        Optional<User> user = userService.getUserByPhone(phone);
        if (user.isPresent()){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserBYEmail(@PathVariable String email){
        Optional<User> userByEmail = userService.getUserByEmail(email);
        if (userByEmail.isPresent()){
            return new ResponseEntity<>(userByEmail, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
}
