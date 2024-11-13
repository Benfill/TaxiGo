package io.benfill.TaxiGo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import io.benfill.TaxiGo.dto.driver.DriverDtoPost;
import io.benfill.TaxiGo.dao.IDriverDao;
import io.benfill.TaxiGo.dto.driver.DriverDtoAnalytics;
import io.benfill.TaxiGo.dto.driver.DriverDtoAvailability;
import io.benfill.TaxiGo.dto.driver.DriverDtoGet;
import io.benfill.TaxiGo.exception.BusinessException;
import io.benfill.TaxiGo.exception.ModelNotFoundException;
import io.benfill.TaxiGo.mapper.DriverMappper;
import io.benfill.TaxiGo.model.Driver;
import io.benfill.TaxiGo.model.Reservation;
import io.benfill.TaxiGo.model.enums.ReservationStatus;
import io.benfill.TaxiGo.repository.DriverRepository;
import io.benfill.TaxiGo.repository.ReservationRepository;
import io.benfill.TaxiGo.service.IDriverService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements IDriverService {

	private final DriverRepository driverRepository;
	private final IDriverDao driverDao;
	private final ReservationRepository reservationRepository;
	private final DriverMappper driverMappper;

	public Optional<Driver> getDriverById(Long id) {
		return driverRepository.findById(id);
	}

	public DriverDtoGet getDriverDetails(long id) {
		Driver driver = getDriverById(id).orElseThrow(() -> new ModelNotFoundException("Driver not found"));
		return driverMappper.convertEntityToDto(driver);
	}

	public List<DriverDtoGet> getAllDrivers() {
		List<Driver> drivers = driverRepository.findAll();
		return drivers.stream().map(d -> driverMappper.convertEntityToDto(d)).collect(Collectors.toList());
	}

	public DriverDtoGet createDriver(DriverDtoPost driverDto) {
		Driver driver = driverRepository.save(driverMappper.convertDtoToEntity(driverDto));
		return driverMappper.convertEntityToDto(driver);
	}

	@Override
	public DriverDtoGet updateDriver(DriverDtoPost driverDto) {
		Driver driver = driverRepository.save(driverMappper.convertDtoToEntity(driverDto));
		return driverMappper.convertEntityToDto(driver);
	}

	@Override
	public void deleteDriver(Long id) throws BusinessException {
		// First check if driver exists
		Driver driver = driverRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException("Driver not found with ID: " + id));

		// Check if driver has any active reservations
		List<Reservation> activeReservations = reservationRepository.findReservationsByDriverId(id).stream()
				.filter(r -> r.getStatus() != null && (ReservationStatus.CONFIRMED.equals(r.getStatus())
						|| ReservationStatus.CREATED.equals(r.getStatus())))
				.collect(Collectors.toList());

		if (!activeReservations.isEmpty()) {
			throw new BusinessException("Cannot delete driver with active reservations");
		}

		// Delete all completed reservations
		List<Reservation> completedReservations = reservationRepository.findReservationsByDriverId(id);
		if (!completedReservations.isEmpty()) {
			reservationRepository.deleteAll(completedReservations);
			 reservationRepository.flush();
		}

		// Finally delete the driver
		try {
			driverRepository.delete(driver);
			driverRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("Failed to delete driver due to data integrity constraints", e);
		}
	}

	@Override
	public Long count() {
		return driverRepository.count();
	}

	@Override
	public DriverDtoAvailability CheckDriverAvailability(Long id) throws RuntimeException {
		Optional<Driver> driverOptional = driverRepository.findById(id);
	    if (!driverOptional.isPresent())
	        throw new ModelNotFoundException("Driver not found with ID: " + id);
		
		Driver driver = driverOptional.get();
		DriverDtoAvailability driverDto = new DriverDtoAvailability();
		
		if(driver.getAvailabilityEnd().isBefore(LocalDateTime.now())) {
			driverDto.setIsAvailable(true);
			driverDto.setMessage("Driver is Available");
		} else {
			driverDto.setIsAvailable(true);
			driverDto.setMessage("Driver is Unavailable");
		}
		return driverDto;
	}

	@Override
	public DriverDtoAnalytics getDriverAnalytics(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
