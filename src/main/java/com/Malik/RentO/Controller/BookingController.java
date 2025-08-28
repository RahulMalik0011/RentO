package com.Malik.RentO.Controller;

import com.Malik.RentO.Entity.Booking;
import com.Malik.RentO.Service.BookingService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create-booking")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking){
        bookingService.createBooking(booking);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/Renter/{renterId}")
    public ResponseEntity<?> getBookingBYRenterId(@PathVariable ObjectId objectId){
        List<Booking> bookingsByRenter = bookingService.getBookingsByRenter(objectId);
        if (!bookingsByRenter.isEmpty()){
            return new ResponseEntity<>(bookingsByRenter, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/Owner/{ownerId}")
    public ResponseEntity<?> getBookingBYOwnerId(@PathVariable ObjectId objectId){
        List<Booking> bookingsByOwner = bookingService.getBookingsByOwner(objectId);
        if (!bookingsByOwner.isEmpty()){
            return new ResponseEntity<>(bookingsByOwner, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable ObjectId objectId){
        List<Booking> bookingsByItem = bookingService.getBookingsByItem(objectId);
        if (!bookingsByItem.isEmpty()){
            return new ResponseEntity<>(bookingsByItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
