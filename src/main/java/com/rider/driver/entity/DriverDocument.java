package com.rider.driver.entity;

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
@Table(name = "DriverDocument")
@IdClass(DriverDocumentId.class)
public class DriverDocument implements Serializable {
    private static final long serialVersionUID = -3308162028408143519L;
    @Id
    @Column(name = "driverId", nullable = false)
    private long driverId;
    @Id
    @Column(name = "docuName", nullable = false)
    private String docuName;
    @Column(name = "docuPath", nullable = false)
    private String docuPath;
    @Column(name = "validatedBy")
    private String validatedBy;
    @Column(name = "remark")
    private String remark;
    @Column(name = "timeCreated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    private LocalDateTime timeCreated;
    @Column(name = "timeUpdated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    private LocalDateTime timeUpdated;
}
