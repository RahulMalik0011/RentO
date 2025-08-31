package com.Malik.RentO.Service;

import com.Malik.RentO.DTO.BookingRequest;
import com.Malik.RentO.Entity.Booking;
import com.Malik.RentO.Entity.Item;
import com.Malik.RentO.Entity.User;
import com.Malik.RentO.Repository.BookingRepository;
import com.Malik.RentO.Repository.ItemRepository;
import com.Malik.RentO.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    public Booking createBooking(BookingRequest dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User renter = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ObjectId itemId = dto.getItemId();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.isAvailable()) {
            throw new RuntimeException("Item is not available for booking");
        }

        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        if (days <= 0) {
            throw new RuntimeException("End date must be after start date");
        }

        User owner = userRepository.findById(item.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        double totalPrice = days * item.getPricePerDay();

        Booking booking = new Booking();
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("PENDING");
        booking.setItemId(item.getId());
        booking.setOwnerId(item.getOwnerId());
        booking.setRenterId(renter.getId());

        // 1️⃣ Save booking first to generate ID
        Booking savedBooking = bookingRepository.save(booking);

        // 2️⃣ Now booking has an ID, add to renter & owner
        renter.getBookingId().add(savedBooking.getId());
        owner.getBookingId().add(savedBooking.getId());

        userRepository.save(renter);
        userRepository.save(owner);

        // 3️⃣ Mark item unavailable
        item.setAvailable(false);
        itemRepository.save(item);

        return savedBooking;
    }

    public List<Booking> getMyBookings(String email) {
        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get booking IDs from user entity
        List<ObjectId> bookingIds = user.getBookingId();

        if (bookingIds == null || bookingIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Fetch bookings by IDs
        return bookingRepository.findByIdIn(bookingIds);
    }

    public Booking getBookingByIdForUser(String email, ObjectId bookingId) {
        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if bookingId is in user's list
        if (!user.getBookingId().contains(bookingId)) {
            throw new RuntimeException("Access denied: Booking not found in your list");
        }

        // Fetch booking
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

}
