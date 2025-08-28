package com.Malik.RentO.Service;

import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

   public void registerUser(User user){
       userRepository.save(user);
   }
   public Optional<User> getUserByEmail(String email){
       return userRepository.findByEmail(email);
   }
   public Optional<User> getUserByPhone(Long phone){
       return userRepository.findByPhone(phone);
   }
   public List<User> getAllUser(){
       return userRepository.findAll();
   }

}
