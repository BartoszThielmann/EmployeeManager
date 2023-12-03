package com.bartoszthielmann.employeemanager.mapper;

import com.bartoszthielmann.employeemanager.dto.ReservationDto;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {WorkspaceMapper.class, UserMapper.class},
        componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {

    ReservationDto reservationToReservationDto(Reservation reservation);
    Reservation reservationDtoToReservation(ReservationDto reservationDto);
}
