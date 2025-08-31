package com.Malik.RentO.Controller;

import com.Malik.RentO.DTO.*;
import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/update-name")
    public ResponseEntity<?> updateName(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> userIndb = userService.getUserByEmail(email);
        if (userIndb.isPresent()){
            User existUser = userIndb.get();
            existUser.setFullName(user.getFullName());
            userService.saveUser(existUser);
           return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found ",HttpStatus.NOT_FOUND);
    }
    @PutMapping("/update-email")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> userIndb = userService.getUserByEmail(email);
        if (userIndb.isPresent()) {
            User existUser = userIndb.get();
            existUser.setEmail(request.getEmail());
            userService.saveUser(existUser);
            return new ResponseEntity<>("Email updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update-phone")
    public ResponseEntity<?> updatePhone(@RequestBody UpdatePhoneRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> userIndb = userService.getUserByEmail(email);
        if (userIndb.isPresent()) {
            User existUser = userIndb.get();
            existUser.setPhone(request.getPhone());
            userService.saveUser(existUser);
            return new ResponseEntity<>("Phone updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> userIndb = userService.getUserByEmail(email);
        if (userIndb.isPresent()) {
            User existUser = userIndb.get();
            existUser.setPassword(request.getPassword());  // Make sure you encode password if needed
            userService.registerUser(existUser);
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    @PutMapping("/update-role")
    public ResponseEntity<?> updateRole(@RequestBody UpdateRoleRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> userIndb = userService.getUserByEmail(email);
        if (userIndb.isPresent()) {
            User existUser = userIndb.get();
            existUser.setUserRoles(request.getUserRoles());
            userService.saveUser(existUser);
            return new ResponseEntity<>("Roles updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    @PutMapping("/update-address")
    public ResponseEntity<?> updateAddress(@RequestBody UpdateAddressRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> userIndb = userService.getUserByEmail(email);
        if (userIndb.isPresent()) {
            User existUser = userIndb.get();

            if (request.getAddress() != null) existUser.setAddress(request.getAddress());
            if (request.getCity() != null) existUser.setCity(request.getCity());
            if (request.getState() != null) existUser.setState(request.getState());
            if (request.getPincode() != null) existUser.setPincode(request.getPincode());

            userService.saveUser(existUser);
            return new ResponseEntity<>("Address updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/about-me")
    public ResponseEntity<?> getUserInformation(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/about-role")
    public ResponseEntity<?> getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()){
            User existUser = user.get();
            return new ResponseEntity<>(existUser.getUserRoles(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/about-item")
    public ResponseEntity<?> getUserItem(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()){
            User existUser = user.get();
            return new ResponseEntity<>(existUser.getItemId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/about-booking")
    public ResponseEntity<?> getUserBooking(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()){
            User existUser = user.get();
            return new ResponseEntity<>(existUser.getBookingId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
        boolean deleted = userService.deleteUserById(id);
        if (deleted) {
            return new ResponseEntity<>("User and their items deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


}
