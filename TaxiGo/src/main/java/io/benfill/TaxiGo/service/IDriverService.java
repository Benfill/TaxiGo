package io.benfill.TaxiGo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import io.benfill.TaxiGo.dto.driver.DriverDtoAnalytics;
import io.benfill.TaxiGo.dto.driver.DriverDtoAvailability;
import io.benfill.TaxiGo.dto.driver.DriverDtoGet;
import io.benfill.TaxiGo.dto.driver.DriverDtoPost;
import io.benfill.TaxiGo.exception.BusinessException;
import io.benfill.TaxiGo.model.Driver;

public interface IDriverService {
	public Optional<Driver> getDriverById(Long id);

	public DriverDtoGet getDriverDetails(long id);

	public List<DriverDtoGet> getAllDrivers(Integer page);

	public DriverDtoGet createDriver(DriverDtoPost driverDto);

	public DriverDtoGet updateDriver(DriverDtoPost driverDto, long id);

	void deleteDriver(Long id) throws BusinessException;

	DriverDtoAvailability CheckDriverAvailability(Long id);

	ResponseEntity<DriverDtoAnalytics> getAnalytics();
	 Driver findAvailableDriverById(Long id);
}
