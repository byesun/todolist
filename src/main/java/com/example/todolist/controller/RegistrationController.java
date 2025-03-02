package com.example.todolist.controller;

import com.example.todolist.db.User;
import com.example.todolist.db.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

/*    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user",new User_1());
        return "user_management/register";
    }*/


    @GetMapping("/register")
    public String addForm(@ModelAttribute("user") User user) {
        return "user_management/register";
    }

    @PostMapping("/register")
    public String save(@Validated @ModelAttribute User user, BindingResult bindingResult) {
        log.info("bindingResult -> {}",bindingResult.getObjectName());
        if(bindingResult.hasErrors()) {
            return "user_management/register";
        }
        userRepository.save(user);
        return "redirect:/login";
    }

//    @PostMapping("/register")
//    public String register(User user, Model model) {
//        if(userRepository.findByUsername(user.getUsername()) != null) {
//            model.addAttribute("error", "사용자의 이름이 존재");
//            log.info("사용자의 이름이 존재 {}",user.toString());
//            return "user_management/register";
//        }
//        log.info("user.toString -> {}", user.toString());
//        userRepository.save(user);
//        return "redirect:/user_management/login";
//    }
}
