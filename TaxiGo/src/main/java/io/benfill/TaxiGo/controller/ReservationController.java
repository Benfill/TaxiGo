package io.benfill.TaxiGo.controller;


import io.benfill.TaxiGo.dto.ReservationAnalytics;
import io.benfill.TaxiGo.dto.ReservationRequest;
import io.benfill.TaxiGo.dto.ReservationResponse;
import io.benfill.TaxiGo.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/reservations")
@Validated
public class ReservationController {

    private final ReservationService reservationService;
    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getReservations(@RequestParam(defaultValue = "0") int pageInt , @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable= PageRequest.of(pageInt, pageSize);
        return ResponseEntity.ok(reservationService.getAllReservations(pageable));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(reservationRequest));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation( @PathVariable Long id ,@Valid @RequestBody ReservationRequest reservation) {
        return ResponseEntity.ok(reservationService.updateReservation(id,reservation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/analytics")
    @Operation(summary = "Obtenir les analytics des réservations")
    public ResponseEntity<ReservationAnalytics> getAnalytics() {
        return ResponseEntity.ok(reservationService.getAnalytics());
    }



}
