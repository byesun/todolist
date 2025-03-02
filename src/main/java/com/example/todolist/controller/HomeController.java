package com.example.todolist.controller;

import com.example.todolist.db.User;
import com.example.todolist.db.UserRepository;
import com.example.todolist.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    private Map<Integer, List<String>> todos = new HashMap<>();

    @PostMapping("/addTodo")
    public String addTodo(String task,String date) {
        int day = Integer.parseInt(date.split("-")[2]);
        todos.computeIfAbsent(day, k->new ArrayList<>()).add(task);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {

        // 세션에서 로그인한 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        User loginUser = (session != null) ? (User) session.getAttribute(SessionConst.LOGIN_USER) : null;

        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser.getUsername()); // 모델에 추가
        } else {
            model.addAttribute("loginUser", "로그인 필요"); // 로그인하지 않은 경우
        }

        LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate",currentDate);
        model.addAttribute("currentMonth",currentDate.format(DateTimeFormatter.ofPattern("yyyy년 M월")));

        // 1부터 29까지의 날짜
        model.addAttribute("days", List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29));
        model.addAttribute("todos",todos);


        return "home";
    }
}
