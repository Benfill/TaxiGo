package io.benfill.TaxiGo.dao;


import io.benfill.TaxiGo.dto.ReservationAnalytics;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class ReservationDao {
    @PersistenceContext
    private EntityManager em;

    @PersistenceContext
    private EntityManager entityManager;

    public ReservationAnalytics getAnalytics() {

        Double averagePricePerKm = (Double) entityManager.createQuery(
                "SELECT AVG(r.price / r.distanceKm) FROM Reservation r WHERE r.distanceKm > 0"
        ).getSingleResult();


        Double averageDistance = (Double) entityManager.createQuery(
                "SELECT AVG(r.distanceKm) FROM Reservation r"
        ).getSingleResult();


        List<Object[]> reservationsByTimeSlot = entityManager.createQuery(
                "SELECT FUNCTION('HOUR', r.dateTime) AS hour, COUNT(r) " +
                        "FROM Reservation r " +
                        "GROUP BY FUNCTION('HOUR', r.dateTime)"
        ).getResultList();

        Map<String, Long> timeSlotMap = reservationsByTimeSlot.stream()
                .collect(Collectors.toMap(
                        obj -> String.format("%02d:00-%02d:00", ((Number) obj[0]).intValue(), ((Number) obj[0]).intValue() + 1),
                        obj -> ((Number) obj[1]).longValue()
                ));


        List<Object[]> popularZones = entityManager.createQuery(
                        "SELECT CONCAT(r.departureAddress, ', ', r.arrivalAddress) AS zone, COUNT(r) " +
                                "FROM Reservation r " +
                                "GROUP BY r.departureAddress, r.arrivalAddress " +
                                "ORDER BY COUNT(r) DESC"
                ).setMaxResults(5) // Limiter aux 5 zones les plus populaires
                .getResultList();

        Map<String, Long> popularZonesMap = popularZones.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> ((Number) obj[1]).longValue()
                ));


        return ReservationAnalytics.builder()
                .averagePricePerKm(averagePricePerKm != null ? averagePricePerKm : 0.0)
                .averageDistance(averageDistance != null ? averageDistance : 0.0)
                .reservationsByTimeSlot(timeSlotMap)
                .popularZones(popularZonesMap)
                .build();
    }



}
