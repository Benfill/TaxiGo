package io.benfill.TaxiGo.service;

import io.benfill.TaxiGo.dto.ReservationAnalytics;
import io.benfill.TaxiGo.dto.ReservationRequest;
import io.benfill.TaxiGo.dto.ReservationResponse;
import io.benfill.TaxiGo.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservation);
    Page<ReservationResponse> getAllReservations(Pageable pageable);
    ReservationResponse getReservation(Long id);
    ReservationResponse updateReservation(Long id,ReservationRequest reservation);
    void deleteReservation(Long id);
    ReservationAnalytics getAnalytics();
}
