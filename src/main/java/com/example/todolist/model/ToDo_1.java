package com.example.todolist.model;

import com.example.todolist.db.User;
import lombok.Data;

@Data
public class ToDo_1 {

    private Long id;

    private String title;


    private String description;
    private Boolean status;
    private String category;

    private User user_id;
}
