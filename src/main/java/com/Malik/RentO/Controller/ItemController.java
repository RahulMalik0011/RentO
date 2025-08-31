package com.Malik.RentO.Controller;

import com.Malik.RentO.DTO.*;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    @GetMapping("/get-allItem")
    public ResponseEntity<?> getAllItem(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> userByEmail = userService.getUserByEmail(email);
        if (userByEmail.isPresent()){
            User user = userByEmail.get();
            List<ObjectId> itemsId = user.getItemId();
            List<Item> items = itemService.getItemsByIds(itemsId);
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(@RequestBody Item item){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            itemService.addItem(item, email);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_MODIFIED);

        }
    }
    @PutMapping("/availability/{id}")
    public ResponseEntity<String> updateAvailability(@PathVariable ObjectId id, @RequestParam boolean available) {
        itemService.updateItemAvailability(id, available);
        return ResponseEntity.ok("Item availability updated successfully!");
    }
    @PutMapping("/update-title/{id}")
    public ResponseEntity<?> updateTitle(@PathVariable String id, @RequestBody UpdateItemTitle dto){
        return updateField(id, item -> item.setTitle(dto.getTitle()));
    }

    // Update description
    @PutMapping("/update-description/{id}")
    public ResponseEntity<?> updateDescription(@PathVariable String id, @RequestBody UpdateItemDescription dto){
        return updateField(id, item -> item.setDescription(dto.getDescription()));
    }

    // Update price
    @PutMapping("/update-price/{id}")
    public ResponseEntity<?> updatePrice(@PathVariable String id, @RequestBody UpdateItemPrice dto){
        return updateField(id, item -> item.setPricePerDay(dto.getPricePerDay()));
    }

    // Update category
    @PutMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody UpdateItemCategory dto){
        return updateField(id, item -> item.setCategory(dto.getCategory()));
    }

    // Update image
    @PutMapping("/update-image/{id}")
    public ResponseEntity<?> updateImage(@PathVariable String id, @RequestBody UpdateItemImage dto){
        return updateField(id, item -> item.setImageUrl(dto.getImageUrl()));
    }

    // Update availability
    @PutMapping("/update-availability/{id}")
    public ResponseEntity<?> updateAvailability(@PathVariable String id, @RequestBody UpdateItemAvailability dto){
        return updateField(id, item -> item.setAvailable(dto.getAvailable()));
    }
    private ResponseEntity<?> updateField(String id, java.util.function.Consumer<Item> updater){
        Optional<Item> itemOpt = itemService.getItemById(new ObjectId(id));
        if (itemOpt.isPresent()){
            Item item = itemOpt.get();
            updater.accept(item);
            itemService.saveItem(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable ObjectId id) {
        boolean deleted = itemService.deleteItemById(id);
        if (deleted) {
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }

}
