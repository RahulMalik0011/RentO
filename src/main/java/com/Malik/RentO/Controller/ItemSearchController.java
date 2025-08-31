package com.Malik.RentO.Controller;

import com.Malik.RentO.Entity.Item;
import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Service.ItemService;
import com.Malik.RentO.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item-search")
public class ItemSearchController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    @GetMapping("/available")
    public ResponseEntity<List<Item>> getAvailableItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> userByEmail = userService.getUserByEmail(email);
        if (userByEmail.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User currentUser = userByEmail.get();
        ObjectId userId = currentUser.getId();

        // Get available items except the user's own
        List<Item> availableItems = itemService.getAvailableItemsExcludingOwner(userId);

        return ResponseEntity.ok(availableItems);
    }
    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<Item>> searchItemByTitle(@PathVariable String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User currentUser = userOpt.get();

        List<Item> items = itemService.findItemByTitle(title)
                .stream()
                .filter(Item::isAvailable)
                .filter(item -> !item.getOwnerId().equals(currentUser.getId())) // exclude my items
                .toList();

        return ResponseEntity.ok(items);
    }


    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<Item>> searchItemByCategory(@PathVariable String category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User currentUser = userOpt.get();

        List<Item> items = itemService.getItemByCategory(category)
                .stream()
                .filter(Item::isAvailable)
                .filter(item -> !item.getOwnerId().equals(currentUser.getId())) // exclude my items
                .toList();

        return ResponseEntity.ok(items);
    }

}
