package com.Malik.RentO.Controller;

import com.Malik.RentO.Entity.Item;
import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Service.ItemService;
import com.Malik.RentO.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @GetMapping("Health-Check")
    public boolean healthCheck(){
        return true;
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        userService.registerUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/available")
    public ResponseEntity<List<Item>> getAvailableItems() {
        return ResponseEntity.ok(itemService.getAvailableItems());
    }

    @GetMapping("/SearchBYTitle/{title}")
    public List<Item> searchItemByTitle(@PathVariable String title){
        List<Item> itemByTitle = itemService.findItemByTitle(title);
        return itemByTitle.stream().filter(Item::isAvailable).toList();
    }

    @GetMapping("/SearchBYCategory/{category}")
    public List<Item> searchItemBYCategory(@PathVariable String category){
        List<Item> itemByCategory = itemService.getItemByCategory(category);
        return itemByCategory.stream().filter(Item::isAvailable).toList();
    }


}
