package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        // templates/home.html 파일을 찾아 렌더링
        return "home";
    }
    
    @GetMapping("/message")
    public String messagesPage() {
        return "message"; // templates/messages/list.html
    }
	
}
