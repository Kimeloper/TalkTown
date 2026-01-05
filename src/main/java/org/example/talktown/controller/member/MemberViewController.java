package org.example.talktown.controller.member;

import org.example.talktown.dto.member.MemberRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MemberViewController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("memberRequest", new MemberRequest());
        return "signup";
    }
}
