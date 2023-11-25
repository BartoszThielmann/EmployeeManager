package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.reservation.ReservationDao;
import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.entity.ReservationDto;
import com.bartoszthielmann.employeemanager.entity.User;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.exception.WorkspaceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    ReservationDao reservationDao;
    @Mock
    UserDao userDao;
    @Mock
    WorkspaceDao workspaceDao;
    @InjectMocks
    ReservationService reservationService;

    ReservationDto reservationDto;
    Date start;
    Date end;
    User user;
    Workspace workspace;

    @BeforeEach
    public void setup() {
        reservationDto = new ReservationDto();
        start = new Date(1700697600); // November 23, 2023 12:00:00 AM
        end = new Date(1700956799); // November 25, 2023 11:59:59 PM
        reservationDto.setStart(start);
        reservationDto.setEnd(end);
        reservationDto.setWorkspaceId(1);
        reservationDto.setUserId(1);

        user = new User();
        user.setId(1);

        workspace = new Workspace();
        workspace.setId(1);
    }


    @Test
    public void test_createReservationFromDto() {
        // given
        given(reservationDao.findWorkspaceReservationsBetweenDates(any(), any(), any()))
                .willReturn(Collections.emptyList());
        given(userDao.findById(reservationDto.getUserId())).willReturn(user);
        given(workspaceDao.findById(reservationDto.getWorkspaceId())).willReturn(workspace);

        // when
        Reservation reservation = reservationService.createReservationFromDto(reservationDto);

        // then
        assertThat(reservationDto.getStart()).isEqualTo(reservation.getStart());
        assertThat(reservationDto.getEnd()).isEqualTo(reservation.getEnd());
        assertThat(reservationDto.getUserId()).isEqualTo(reservation.getUser().getId());
        assertThat(reservationDto.getWorkspaceId()).isEqualTo(reservation.getWorkspace().getId());
    }

    @Test
    public void test_createReservationFromFormThrowsWorkspaceNotAvailableException() {
        // given
        given(reservationDao.findWorkspaceReservationsBetweenDates(any(), any(), any()))
                .willReturn(Arrays.asList(new Reservation()));

        // then/when
        assertThrows(WorkspaceNotAvailableException.class,
                () -> reservationService.createReservationFromDto(reservationDto));
    }
}
