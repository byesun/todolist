package com.example.todolist.service;

import com.example.todolist.db.User;
import com.example.todolist.db.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String username, String password) {
        Optional<User> findUser = userRepository.findByUsername(username);
        log.info("userRepository.findByUsername -> {}", findUser);
        if(findUser.isPresent()) {
            User user = findUser.get();
            if(user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
/*        return userRepository.findByUsername(username)
                .filter(m -> new BCryptPasswordEncoder().matches(password,m.getPassword()))
                .orElse(null);*/
    }
}
