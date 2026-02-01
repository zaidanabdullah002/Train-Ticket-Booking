package org.services;

import org.data.UserRepository;
import org.entities.User;
import org.mindrot.jbcrypt.BCrypt;
import org.util.PasswordUtil;

import java.util.UUID;

public class UserService {
    UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User signUp(String username,String password){
        if(userRepository.findByUsername(username)!=null){
            throw new RuntimeException("User already exist , login"+ username);
        }
        String hashedPass = PasswordUtil.hash(password);
        User user = new User(
                UUID.randomUUID().toString(),
                username,
                hashedPass
        );
        userRepository.save(user);
        System.out.println("Signed up successfully");
        return user;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new RuntimeException("User not found, please signup");

        if (!PasswordUtil.verify(password, user.passwordHash)) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

}
