package com.rider.driver.repo;

import com.rider.driver.entity.DriverCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverOnboardingRepository extends JpaRepository<DriverCredential, Long> {
    public DriverCredential getByUsername(String username);
}
