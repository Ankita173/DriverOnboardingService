package com.rider.driver.util;

import com.rider.driver.entity.Driver;
import com.rider.driver.entity.DriverCredential;
import com.rider.driver.model.DriverOnboardingRequest;
import com.rider.driver.model.DriverRequest;
import com.rider.driver.model.DriverStatus;
import com.rider.driver.util.DriverMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DriverMapperTest {
    @Test
    public void driverRequestMapperTest() {
        Driver driver = driver();
        DriverMapper.driverRequestMapper(driver, driverRequest());
        Assertions.assertEquals(driver.getPhoneNo(), 12345668);
        Assertions.assertEquals(driver.getUsername(), "dummy");
    }

    @Test
    public void driverProfileMapperTest() {
        Driver driver = DriverMapper.driverProfileMapper(driverCredential());
        Assertions.assertEquals(driver.getDriverId(), 1);
        Assertions.assertEquals(driver.getUsername(), "dummy");
        Assertions.assertEquals(driver.getStatus(), DriverStatus.Accepted);
        Assertions.assertEquals(driver.getRemark(), "New driver created");
    }

    @Test
    public void driverOnboardingMapperTest() {
        DriverCredential driver = DriverMapper.driverOnboardingMapper(onboardingRequest());
        Assertions.assertEquals(driver.getUsername(), "dummy");
        Assertions.assertEquals(driver.getPassword(), "YWJj");
    }

    @Test
    public void encryptionTextTest() {
        String secret = DriverMapper.getEncryptedText("mySecret");
        Assertions.assertEquals(secret, "bXlTZWNyZXQ=");
    }


    private DriverOnboardingRequest onboardingRequest() {
        return DriverOnboardingRequest.builder()
                .username("dummy")
                .password("abc").build();
    }

    private Driver driver() {
        return Driver.builder()
                .driverId(1)
                .username("dummy")
                .firstName("dummy")
                .lastName("surname")
                .phoneNo(87654321).build();
    }

    private DriverRequest driverRequest() {
        return DriverRequest.builder()
                .phoneNo(12345668)
                .zipCode(834892)
                .address("ABC colony").build();
    }

    private DriverCredential driverCredential() {
        return DriverCredential.builder()
                .username("dummy")
                .password("834892")
                .driverId(1).build();
    }
}
