package org.entities;

import java.time.LocalDate;

public class Ticket {
    String pnr;
    public String userId;
    int trainNo;
    String trainName;

    String source;
    String destination;
    LocalDate travelDate;

    String seatType;   // "AC" or "SLEEPER"

    public Ticket(){

    }

    public Ticket(String id, String userId, Integer trainNo,String trainName,
                  LocalDate date, String seatType,String src,
                  String dest) {
        this.pnr = id;
        this.userId = userId;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.travelDate = date;
        this.seatType = seatType;
        this.source = src;
        this.destination = dest;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + pnr + '\'' +
                ", trainNo=" + trainNo +
                ", trainName=" + trainName +
                ", date='" + travelDate + '\'' +
                ", seatType='" + seatType + '\'' +
                ", from='" + source + '\'' +
                ", to='" + destination + '\'' +
                '}';
    }
}
