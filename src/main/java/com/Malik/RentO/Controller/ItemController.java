package com.Malik.RentO.Controller;

import com.Malik.RentO.Entity.Item;
import com.Malik.RentO.Service.ItemService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    @GetMapping("/available")
    public ResponseEntity<List<Item>> getAvailableItems() {
        return ResponseEntity.ok(itemService.getAvailableItems());
    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(@RequestBody Item item){
        itemService.addItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
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

    @PutMapping("/availability/{id}")
    public ResponseEntity<String> updateAvailability(@PathVariable ObjectId id, @RequestParam boolean available) {
        itemService.updateItemAvailability(id, available);
        return ResponseEntity.ok("Item availability updated successfully!");
    }
}
