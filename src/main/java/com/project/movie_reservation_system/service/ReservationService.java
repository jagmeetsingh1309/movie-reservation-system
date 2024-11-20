package com.project.movie_reservation_system.service;

import com.project.movie_reservation_system.dto.ReservationRequestDto;
import com.project.movie_reservation_system.entity.Reservation;
import com.project.movie_reservation_system.entity.Seat;
import com.project.movie_reservation_system.enums.ReservationStatus;
import com.project.movie_reservation_system.enums.SeatStatus;
import com.project.movie_reservation_system.exception.*;
import com.project.movie_reservation_system.repository.ReservationRepository;
import com.project.movie_reservation_system.repository.SeatRepository;
import com.project.movie_reservation_system.repository.ShowRepository;
import com.project.movie_reservation_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static com.project.movie_reservation_system.constant.ExceptionMessages.*;

@Service
public class ReservationService {

    private final SeatLockManager seatLockManager;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationService(SeatLockManager seatLockManager,
                              ReservationRepository reservationRepository,
                              SeatRepository seatRepository,
                              ShowRepository showRepository,
                              UserRepository userRepository) {
        this.seatLockManager = seatLockManager;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Reservation createReservation(ReservationRequestDto reservationRequestDto, String currentUserName) {

        return showRepository
                .findById(reservationRequestDto.getShowId())
                .map(show -> {
                    List<Seat> seats = reservationRequestDto
                            .getSeatIdsToReserve()
                            .stream()
                            .map(seatRepository::findById)
                            .map(Optional::get)
                            .toList();

                    // Calculate the amount to be paid.
                    Double amountToBePaid = seats.stream().map(Seat::getPrice).reduce(0.0, Double::sum);

                    if(reservationRequestDto.getAmount() != amountToBePaid)
                        throw new AmountNotMatchException(AMOUNT_NOT_MATCH, HttpStatus.BAD_REQUEST);

                    // Acquire the lock for all seats
                    seats.forEach(seat -> {
                        ReentrantLock seatLock = seatLockManager.getLockForSeat(seat.getId());
                        boolean isLockFree = seatLock.tryLock();
                        if (!isLockFree){
                            throw new SeatLockAcquiredException(SEAT_LOCK_ACQUIRED, HttpStatus.CONFLICT);
                        }
                    });

                    boolean anyBookedSeat = seats.stream().map(Seat::getStatus).anyMatch(seatStatus -> seatStatus.equals(SeatStatus.BOOKED));

                    if (anyBookedSeat){
                        // Remove lock for every seat
                        seats.forEach(seat -> seatLockManager.removeLockForSeat(seat.getId()));
                        throw new SeatAlreadyBookedException(SEAT_ALREADY_BOOKED, HttpStatus.BAD_REQUEST);
                    }

                    // Mark all the seats as booked
                    List<Seat> bookedSeats = seats.stream().map(seat -> {
                        seat.setStatus(SeatStatus.BOOKED);
                        return seatRepository.save(seat);
                    }).toList();


                    // Create the reservation
                    Reservation reservation = Reservation.builder()
                            .reservationStatus(ReservationStatus.BOOKED)
                            .seatsReserved(bookedSeats)
                            .show(show)
                            .user(userRepository.findByUsername(currentUserName).get())
                            .amountPaid(reservationRequestDto.getAmount())
                            .createdAt(LocalDateTime.now())
                            .build();

                    // Remove lock for every seat
                    seats.forEach(seat -> seatLockManager.removeLockForSeat(seat.getId()));

                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new ShowNotFoundException(SHOW_NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    public Reservation getReservationById(long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND, HttpStatus.NOT_FOUND));

    }

    public Reservation cancelReservation(long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservationIdb -> {
                    if (LocalDateTime.now().isAfter(reservationIdb.getShow().getStartTime()))
                        throw new ShowStartedException(SHOW_STARTED_EXCEPTION, HttpStatus.BAD_REQUEST);

                    reservationIdb.getSeatsReserved()
                            .forEach(seat -> {
                                seat.setStatus(SeatStatus.UNBOOKED);
                                seatRepository.save(seat);
                            });

                    reservationIdb.setReservationStatus(ReservationStatus.CANCELED);
                    return reservationRepository.save(reservationIdb);
                })
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND, HttpStatus.NOT_FOUND));


    }
}
