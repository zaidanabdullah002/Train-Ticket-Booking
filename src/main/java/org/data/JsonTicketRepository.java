package org.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.entities.Ticket;
import org.entities.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonTicketRepository implements TicketRepository {

    private static final String FILE_PATH = "src/main/resources/tickets.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public synchronized void save(User user, Ticket ticket) {
        try {
            File file = new File(FILE_PATH);

            List<Ticket> tickets;

            if (file.exists()) {
                tickets = mapper.readValue(
                        file,
                        new TypeReference<List<Ticket>>() {}
                );
            } else {
                tickets = new ArrayList<>();
            }

            tickets.add(ticket);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, tickets);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save ticket", e);
        }
    }
    @Override
    public List<Ticket> findByUserId(String userId) {
        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return new ArrayList<>();
            }

            List<Ticket> allTickets = mapper.readValue(
                    file,
                    new TypeReference<List<Ticket>>() {}
            );

            return allTickets.stream()
                    .filter(t -> t.userId.equals(userId))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch tickets", e);
        }
    }
}
