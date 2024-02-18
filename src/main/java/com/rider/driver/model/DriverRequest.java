package com.rider.driver.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class DriverRequest {
    private String firstName;
    private String lastName;
    private String driverLicense;
    private long phoneNo;
    private String address;
    private long zipCode;

}
