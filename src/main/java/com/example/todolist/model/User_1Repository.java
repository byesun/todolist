/*
package com.example.todolist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class User_1Repository {

    private static Map<Long, User_1> store = new ConcurrentHashMap<>();

    private static long sequnce = 0L;

    public void test_data() {
        User_1 user = new User_1();
        user.setUsername("rkdqudtjs");
        user.setPassword("123123");
        user.setEmail("rnrnrrnrnr9@nmaver.com");
    }

    public User_1 save(User_1 user1) {
        user1.setId(++sequnce);
        log.info("user save -> {}",user1);
        store.put(user1.getId(),user1);
        return user1;
    }

//    public User_1 findByUsername(String username) {
//        return users.stream()
//                .filter(user -> user.getUsername().equals(username))
//                .findFirst()
//                .orElse(null);
//    }

    public Optional<User_1> findByUsername(String username) {
        return findAll().stream()
                .filter(m -> m.getUsername().equals(username))
                .findFirst();
    }

    public List<User_1> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {store.clear();}
}
*/
