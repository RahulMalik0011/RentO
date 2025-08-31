package com.Malik.RentO.DTO;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;
@Data
public class BookingRequest {
    private ObjectId itemId;        // String -> will be converted to ObjectId
    private LocalDate startDate;
    private LocalDate endDate;
}
