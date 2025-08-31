package com.Malik.RentO.Service;

import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Repository.ItemRepository;
import com.Malik.RentO.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   public void registerUser(User user){
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setUserRoles(Arrays.asList("USER","Renter"));
       userRepository.save(user);
   }
   public void saveUser(User user){
       userRepository.save(user);
   }
   public Optional<User> getUserByEmail(String email){
       return userRepository.findByEmail(email);
   }
    public boolean deleteUserById(ObjectId id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Delete all items owned by this user
            List<ObjectId> itemIds = user.getItemId();
            itemIds.forEach(itemId -> itemRepository.deleteById(itemId));

            // Delete the user
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
