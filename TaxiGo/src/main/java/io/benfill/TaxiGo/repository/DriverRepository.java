package io.benfill.TaxiGo.repository;

import io.benfill.TaxiGo.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.benfill.TaxiGo.model.Driver;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {


    Optional<Driver> findByIdAndStatus(Long id, Status status);
}
