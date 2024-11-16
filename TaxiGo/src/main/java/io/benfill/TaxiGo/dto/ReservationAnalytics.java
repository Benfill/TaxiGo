package io.benfill.TaxiGo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAnalytics {

    private Double averagePricePerKm;


    private Double averageDistance;


    private Map<String, Long> reservationsByTimeSlot;


    private Map<String, Long> popularZones;
}