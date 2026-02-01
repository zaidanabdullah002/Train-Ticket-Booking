package org.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.entities.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonUserRepository implements UserRepository {

    private static final String FILE = "src/main/resources/users.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public synchronized void save(User user) {
        try {
            List<User> users = findAll();
            users.add(user);
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE), users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByUsername(String username) {
        return findAll().stream()
                .filter(u -> u.username.equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        try {
            File file = new File(FILE);
            if (!file.exists()) return new ArrayList<>();

            return mapper.readValue(
                    file,
                    new TypeReference<List<User>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
