package com.Malik.RentO.DTO;

import lombok.Data;

@Data
public class UpdateAddressRequest {
    private String address;
    private String city;
    private String state;
    private Long pincode;
}
