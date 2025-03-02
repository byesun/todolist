package com.example.todolist.controller;

import com.example.todolist.answerai.TogetherAIAnswer;
import com.example.todolist.db.User;
import com.example.todolist.service.LoginService;
import com.example.todolist.session.SessionConst;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
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
public class LoginController {

    private final LoginService loginService;

    private final TogetherAIAnswer togetherAIAnswer;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginRequest") User user, Model model) throws IOException {
        String question = togetherAIAnswer.generateQuestion("공부하기");// 할일 입력
        log.info("question -> {}",question);
        String userAnswer = "Spring Boot에 대해 학습을 진행했습니다.";
        String s = togetherAIAnswer.checkAnswer("공부하기", userAnswer);
        log.info("질문에 대한 적절함 판단 -> {}",s);

        log.info("user.toString() {}",user.toString());
        return "user_management/login"; // login.html 뷰를 반환
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute User user, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "user_management/login";
        }
        User loginUser = loginService.login(user.getUsername(), user.getPassword());
        log.info("login? {}",loginUser);
        if(loginUser == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치 하지 않습니다.");
            return "user_management/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER,loginUser);

        log.info("세션 저장 완료 -> {}",session.getAttribute(SessionConst.LOGIN_USER));


        return "redirect:" + "/home";
/*        User_1 user = userRepository.findByUsername(username.getUsername());

        if (user != null && user.getPassword().equals(username.getPassword())) {
            // 로그인 성공 처리
            model.addAttribute("username", user.getUsername());
            return "redirect:home"; // 홈 페이지로 리다이렉트
        }

        model.addAttribute("error", "Invalid username or password");
        return "user_management/login"; // 로그인 실패 시 다시 로그인 페이지로*/
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
