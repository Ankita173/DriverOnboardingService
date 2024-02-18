package com.rider.driver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDocumentId implements Serializable {
    private static final long serialVersionUID = 6979955169205397523L;
    private long driverId;
    private String docuName;
}
