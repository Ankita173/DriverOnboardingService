package com.rider.driver.util;

import com.rider.driver.entity.Driver;
import com.rider.driver.entity.DriverCredential;
import com.rider.driver.entity.DriverDocument;
import com.rider.driver.model.DriverOnboardingRequest;
import com.rider.driver.model.DriverRequest;
import com.rider.driver.model.DriverStatus;
import com.rider.driver.model.DriverValidationRequest;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

public class DriverMapper {
    public static void driverRequestMapper(Driver driver, DriverRequest driverRequest) {
        driver.setFirstName(Objects.isNull(driverRequest.getFirstName()) ?
                driver.getFirstName() : driverRequest.getFirstName());
        driver.setLastName(Objects.isNull(driverRequest.getLastName()) ?
                driver.getLastName() : driverRequest.getLastName());
        driver.setDriverLicense(Objects.isNull(driverRequest.getDriverLicense()) ?
                driver.getDriverLicense() : driverRequest.getDriverLicense());
        driver.setPhoneNo(Objects.isNull(driverRequest.getPhoneNo()) ?
                driver.getPhoneNo() : driverRequest.getPhoneNo());
        driver.setAddress(Objects.isNull(driverRequest.getAddress()) ?
                driver.getAddress() : driverRequest.getAddress());
        driver.setZipCode(Objects.isNull(driverRequest.getZipCode()) ?
                driver.getZipCode() : driverRequest.getZipCode());
    }

    public static Driver driverProfileMapper(DriverCredential driverRequest) {
        return Driver.builder()
                .driverId(driverRequest.getDriverId())
                .username(driverRequest.getUsername())
                .timeCreated(driverRequest.getTimeCreated())
                .timeUpdated(driverRequest.getTimeUpdated())
                .status(DriverStatus.Accepted)
                .remark("New driver created")
                .build();
    }
    public static DriverCredential driverOnboardingMapper(DriverOnboardingRequest request) {
        return DriverCredential.builder()
                .username(request.getUsername())
                .password(getEncryptedText(request.getPassword())).build();
    }

    public static void driverValidateDocumentMapper(DriverDocument driverDoc, DriverValidationRequest driverRequest) {
        driverDoc.setValidatedBy(driverRequest.getValidatedBy());
        driverDoc.setValidatedBy(driverRequest.getRemark());
        driverDoc.setTimeUpdated(LocalDateTime.now());
    }

    public static String getEncryptedText(String secret) {
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }
}
