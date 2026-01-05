package org.example.talktown.controller.myPage;

import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Member;
import org.example.talktown.service.member.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/myPage")
    public String myPage(Model model, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        String email = authentication.getName();

        model.addAttribute("member", member);
        return "myPage";
    }

}
