package io.benfill.TaxiGo.service;

import io.benfill.TaxiGo.dao.VehicleAnalyticsDto;
import io.benfill.TaxiGo.dto.request.VehicleRequestDto;
import io.benfill.TaxiGo.dto.response.VehicleResponseDto;
import io.benfill.TaxiGo.model.Vehicle;

import javax.validation.Valid;
import java.util.List;

public interface VehicleService {
    VehicleResponseDto saveVehicle(@Valid  VehicleRequestDto vehicleRequestDto);
    VehicleResponseDto updateVehicle(Long id, @Valid VehicleRequestDto vehicleRequestDto);
    String deleteVehicle(Long id);
    VehicleAnalyticsDto getAnalytics();
    List<VehicleResponseDto> getAllVehicles();
    VehicleResponseDto getVehicleById(Long id);
   Vehicle findAvailableVehiculeById(Long id);
}
