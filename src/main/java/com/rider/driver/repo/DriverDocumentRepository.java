package com.rider.driver.repo;

import com.rider.driver.entity.DriverDocument;
import com.rider.driver.entity.DriverDocumentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverDocumentRepository extends JpaRepository<DriverDocument, DriverDocumentId> {
    public void deleteByDriverId(long driverId);
}
