package io.benfill.TaxiGo.service.impl;

import io.benfill.TaxiGo.dao.ReservationDao;
import io.benfill.TaxiGo.dto.ReservationAnalytics;
import io.benfill.TaxiGo.dto.ReservationRequest;
import io.benfill.TaxiGo.dto.ReservationResponse;
import io.benfill.TaxiGo.mapper.ReservationMapper;
import io.benfill.TaxiGo.model.Driver;
import io.benfill.TaxiGo.model.Reservation;
import io.benfill.TaxiGo.model.Vehicle;
import io.benfill.TaxiGo.model.enums.VehicleType;
import io.benfill.TaxiGo.repository.ReservationRepository;
import io.benfill.TaxiGo.service.IDriverService;
import io.benfill.TaxiGo.service.ReservationService;
import io.benfill.TaxiGo.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl  implements ReservationService {


    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final IDriverService driverService;
    private final VehicleService vehiculeService;
    private  final ReservationDao reservationDao;



    @Override
    public ReservationResponse createReservation(ReservationRequest reservation) {

     Driver driver=  driverService.findAvailableDriverById(reservation.getDriver_id());



        Vehicle vehicule = vehiculeService.findAvailableVehiculeById(reservation.getVehicle_id());


        Reservation reservationEntity = reservationMapper.toEntity(reservation);

System.out.println(vehicule.getType());
        double prix = calculatePrice(reservation.getDistanceKm(), vehicule.getType());
        reservationEntity.setPrice(prix);
        reservationEntity.setDriver(driver);
        reservationEntity.setVehicle(vehicule);
        Reservation savedReservation = reservationRepository.save(reservationEntity);
        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    public Page<ReservationResponse> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::toResponse);
    }

    @Override
    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Réservation introuvable."));
        return reservationMapper.toResponse(reservation);
    }

    @Override
    public ReservationResponse updateReservation(Long id, ReservationRequest reservation) {
        return null;
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Réservation introuvable."));
        reservationRepository.delete(reservation);
    }
    @Override
    public ReservationAnalytics getAnalytics() {
        return reservationDao.getAnalytics();
    }




    private double calculatePrice(double distance, VehicleType type) {
        switch (type) {
            case SEDAN:
                return distance * 5;
            case VAN:
                return distance * 7;
            case MINIBUS:
                return distance * 9;
            default:
                throw new IllegalArgumentException("Type de véhicule invalide.");
        }
}}
