package com.Malik.RentO.Service;

import com.Malik.RentO.Entity.Booking;
import com.Malik.RentO.Repository.BookingRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public void createBooking(Booking booking){
        bookingRepository.save(booking);
    }
    public List<Booking> getBookingsByRenter(ObjectId renterId) {
        return bookingRepository.findByRenterId(renterId);
    }
    public List<Booking> getBookingsByOwner(ObjectId renterId) {
        return bookingRepository.findByOwnerId(renterId);
    }
    public List<Booking> getBookingsByItem(ObjectId itemId) {
        return bookingRepository.findByItemId(itemId);
    }
}
