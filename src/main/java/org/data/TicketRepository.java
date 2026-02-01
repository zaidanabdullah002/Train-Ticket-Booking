package org.data;

import org.entities.Ticket;
import org.entities.User;

import java.util.List;

public interface TicketRepository {
    void save(User user, Ticket ticket);
    List<Ticket> findByUserId(String userId); //to show how many tickets user has
}
