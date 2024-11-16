package io.benfill.TaxiGo.mapper;

import io.benfill.TaxiGo.dto.ReservationRequest;
import io.benfill.TaxiGo.dto.ReservationResponse;
import io.benfill.TaxiGo.model.Reservation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReservationMapper {



    @Mapping(target = "id" ,ignore = true)
    Reservation toEntity(ReservationRequest reservationRequest);

    ReservationResponse toResponse(Reservation reservation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ReservationRequest reservationRequest, Reservation reservation);
}
