package io.benfill.TaxiGo.repository;

import io.benfill.TaxiGo.model.Vehicle;
import io.benfill.TaxiGo.model.enums.Status;
import io.benfill.TaxiGo.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("SELECT AVG(v.mileage) FROM Vehicle v WHERE v.type = :type")
    Double findAverageMileageByType(VehicleType type);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.type = :type AND v.status = 'IN_PROGRESS'")
    Long countVehiclesInProgressByType(VehicleType type);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status")
    Long countVehiclesByStatus(Status status);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.type = :type")
    Long countVehiclesByType(VehicleType type);

     Optional<Vehicle> findByIdAndStatus(Long id, Status status);


}
