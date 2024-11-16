package io.benfill.TaxiGo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.benfill.TaxiGo.model.enums.ReservationStatus;
import io.benfill.TaxiGo.utils.AddressConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "date_time", nullable = false)
	private LocalDateTime dateTime;

	@Convert(converter = AddressConverter.class)
	@Column(name = "departure_address", nullable = false)
	private String departureAddress;

	@Convert(converter = AddressConverter.class)
	@Column(name = "arrival_address", nullable = false)
	private String arrivalAddress;

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private double price;

	@Column(name = "start_time_course", nullable = false)
	private Integer startTimeCourse;

	@Column(name = "end_time_course", nullable = false)
	private Integer endTimeCourse;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	@Column(name = "distance_km", nullable = false, precision = 10, scale = 2)
	private BigDecimal distanceKm;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id", nullable = false)
	private Driver driver;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;

}
