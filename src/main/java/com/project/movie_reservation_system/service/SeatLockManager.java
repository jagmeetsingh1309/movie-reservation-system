package com.project.movie_reservation_system.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Scope("singleton")
public class SeatLockManager {

    private ConcurrentHashMap<Long, ReentrantLock> seatLocks = new ConcurrentHashMap<>();

    public ReentrantLock getLockForSeat(long seatId){
        return seatLocks.computeIfAbsent(seatId, id -> new ReentrantLock());
    }

    public void removeLockForSeat(long seatId){
        seatLocks.remove(seatId);
    }
}
