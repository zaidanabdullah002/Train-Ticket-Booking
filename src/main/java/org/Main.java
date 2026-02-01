package org;

import org.controller.UserController;
import org.data.JsonSeatRepository;
import org.data.JsonTicketRepository;
import org.data.JsonUserRepository;
import org.data.TrainRepository;
import org.entities.Ticket;
import org.services.BookingService;
import org.services.TrainService;
import org.entities.User;
import org.services.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // wiring (manual DI)
        UserController userController =
                new UserController(
                        new UserService(
                                new JsonUserRepository()
                        )
                );

        TrainService trainService =
                new TrainService(
                        new TrainRepository()
                );

        BookingService bookingService = null;
        User currentUser = null;
        LocalDate travelDate = LocalDate.now();

        System.out.println("Train Ticket App started");

        while (true) {
            System.out.println("\n1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Search Train");
            System.out.println("4. Book Ticket");
            System.out.println("5. Exit\n");
            System.out.println("Enter the option");


            String input = scanner.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }


            try {
                switch (choice) {

                    case 1 -> {
                        System.out.print("Username: ");
                        String u = scanner.nextLine();
                        System.out.print("Password: ");
                        String p = scanner.nextLine();

                        currentUser = userController.signup(u, p);
                        bookingService = new BookingService(
                                currentUser,
                                new JsonSeatRepository(),
                                new JsonTicketRepository(),
                                new TrainRepository()
                        );
                        System.out.println("Signup successful");
                    }

                    case 2 -> {
                        System.out.print("Username: ");
                        String u = scanner.nextLine();
                        System.out.print("Password: ");
                        String p = scanner.nextLine();

                        currentUser = userController.login(u, p);
                        bookingService = new BookingService(
                                currentUser,
                                new JsonSeatRepository(),
                                new JsonTicketRepository(),
                                new TrainRepository());
                        System.out.println("Login successful");
                    }

                    case 3 -> {
                        if (currentUser == null) {
                            System.out.println("Please login first");
                            break;
                        }

                        System.out.print("Source: ");
                        String src = scanner.nextLine();
                        System.out.print("Destination: ");
                        String dst = scanner.nextLine();

                        trainService.search(src, dst)
                                .forEach(t ->
                                        System.out.println(
                                                t.trainNo + " " + t.name
                                        )
                                );
                    }

                    case 4 -> {
                        if (currentUser == null) {
                            System.out.println("Please login first");
                            break;
                        }

                        System.out.print("Train No: ");
                        int trainNo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter source station : ");
                        String source = scanner.nextLine();

                        System.out.print("Enter destination station : ");
                        String destination = scanner.nextLine();
                        if (bookingService != null) {
                            bookingService.showSeats(trainNo,travelDate);
                        }
                        System.out.print("Seat Type (AC/Sleeper): ");
                        String seatType = scanner.nextLine();

                        try{
                            Ticket ticket = null;
                            if (bookingService != null) {
                                ticket = bookingService.bookSeat(
                                        trainNo,
                                        travelDate,
                                        seatType,
                                        source,
                                        destination
                                );
                            }
                            System.out.println("Ticket booked");
                            System.out.println(ticket);
                        }catch (NullPointerException e){
                            System.out.println("tickets already sold");
                        }

                    }

                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }

                    default -> System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
