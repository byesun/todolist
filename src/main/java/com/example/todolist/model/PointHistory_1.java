package com.example.todolist.model;

import com.example.todolist.db.User;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PointHistory_1 {

    private Long id;

    private User user_id;

    private Integer pointsChanged;
    private String reason;
    private LocalDateTime timestamp;

    private void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
