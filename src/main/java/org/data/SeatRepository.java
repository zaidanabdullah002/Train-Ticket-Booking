package org.data;

import org.entities.SeatAvailability;

public interface SeatRepository {
    SeatAvailability find(int TrainNo, String date);
    void save(SeatAvailability seatAvailability);
    SeatAvailability showSeats(int TrainNo,String date);
}
