package com.rider.driver.entity;

import com.rider.driver.model.DriverStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Driver")
public class Driver implements Serializable {
    private static final long serialVersionUID = 3841926719187660774L;
    @Id
    private long driverId;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "driverLicense")
    private String driverLicense;
    @Column(name = "phoneNo")
    private long phoneNo;
    @Column(name = "address")
    private String address;
    @Column(name = "zipCode")
    private long zipCode;
    @Column(name = "status", columnDefinition = "varchar(32) default 'Accepted'")
    @Enumerated(value = EnumType.STRING)
    private DriverStatus status = DriverStatus.Accepted;

    @Column(name = "remark")
    private String remark;
    @Column(name = "timeCreated",  columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    private LocalDateTime timeCreated;
    @Column(name = "timeUpdated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    private LocalDateTime timeUpdated;
}
