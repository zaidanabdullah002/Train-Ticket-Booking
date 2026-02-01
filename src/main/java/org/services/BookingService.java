package org.services;

import org.data.*;
import org.entities.SeatAvailability;
import org.entities.Ticket;
import org.entities.User;

import java.time.LocalDate;
import java.util.*;

public class BookingService {
    User currentUser;
    private final SeatRepository seatRepository;
    TicketRepository ticketRepository;
    TrainRepository trainRepository;
    public BookingService(User user, JsonSeatRepository seatRepository,
                          JsonTicketRepository ticketRepository, TrainRepository trainRepository) {
        currentUser = user;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.trainRepository = trainRepository;
    }

    public Ticket bookSeat(Integer trainNo, LocalDate date,String seatType,String src,String dest){
        String travelDate = date.toString(); // yyyy-MM-dd

        SeatAvailability availability =
                seatRepository.find(trainNo, travelDate);

        if (availability == null) {
            throw new RuntimeException("No data for train/date");
        }
        synchronized (availability){
            if(Objects.equals(seatType, "AC")){
                if(availability.acAvailable <= 0){
                    throw new RuntimeException("no AC seats availaible");
                }else{
                    availability.acAvailable--;
                }
            }
            else if(Objects.equals(seatType,"Sleeper")){
                if(availability.sleeperAvailable <= 0){
                    throw new RuntimeException("no sleeperAvailable seats availaible");
                }else{
                    availability.sleeperAvailable--;
                }
            }
        }
        Ticket ticket = new Ticket(
                UUID.randomUUID().toString(),
                currentUser.userId,
                trainNo,
                trainRepository.getTrainName(trainNo),
                date,
                seatType,
                src,
                dest
        );
        seatRepository.save(availability);
        ticketRepository.save(currentUser,ticket);
        return ticket;
    }

    public void showSeats(int trainNo, LocalDate travelDate) {
        SeatAvailability seatAvailability = seatRepository.showSeats(trainNo,travelDate.toString());
        System.out.println("Seats Available in AC : "+seatAvailability.acAvailable);
        System.out.println("Seats Available in SLEEPER : "+seatAvailability.sleeperAvailable);
    }
}
