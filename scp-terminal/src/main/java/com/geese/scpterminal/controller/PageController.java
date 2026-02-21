package com.geese.scpterminal.controller;

import com.geese.scpterminal.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final AuthService authService;

    public PageController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        return authService.login(username, password)
                .map(user -> {
                    session.setAttribute("scopedUser", user);
                    return "redirect:/terminal";
                })
                .orElse("redirect:/login?error=true");
    }

    @GetMapping("/terminal")
    public String terminal(HttpSession session) {
        if (session.getAttribute("scopedUser") == null) {
            return "redirect:/login";
        }
        return "terminal";
    }
}