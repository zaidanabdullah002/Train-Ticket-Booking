package org.data;

import org.entities.User;
import java.util.List;

public interface UserRepository {
    void save(User user);
    User findByUsername(String username);
    List<User> findAll();
}
