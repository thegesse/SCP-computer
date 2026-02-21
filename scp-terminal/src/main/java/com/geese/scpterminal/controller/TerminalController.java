package com.geese.scpterminal.controller;

import com.geese.scpterminal.model.Users;
import com.geese.scpterminal.service.AiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/terminal")
public class TerminalController {
    private final AiService aiService;

    public TerminalController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String topic, HttpSession session) {
        Users user = (Users) session.getAttribute("scopedUser");
        if (user == null) return "ACCESS DENIED: SESSION EXPIRED.";

        return aiService.generateFile(user.getClearanceLevel(), topic);
    }
}