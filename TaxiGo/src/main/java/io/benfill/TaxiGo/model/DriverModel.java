package io.benfill.TaxiGo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import io.benfill.TaxiGo.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
public @Data class DriverModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
    //	private Status status;
	
	@Column(name = "availability_start", nullable = false)
	private LocalDateTime availabilityStart;
	@Column(name = "availability_end", nullable = false)
	private LocalDateTime availabilityend;

}