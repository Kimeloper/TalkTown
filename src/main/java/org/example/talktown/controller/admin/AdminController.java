package org.example.talktown.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Member;
import org.example.talktown.service.member.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/admin")
    public String admin(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "keyword", defaultValue = "") String keyword){

        Page<Member> members = null;

        if(keyword == null){//검색 안했을때
            members = memberService.findAllMembers(pageable);
        }else{//검색 했을때
            members = memberService.searchMember(keyword, pageable);
        }

        int nowPage = members.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, members.getTotalPages());

        model.addAttribute("members", members);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin";
    }
    
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(value = "id") long id) {
        memberService.delete(id);
        System.out.println(id + "번째 회원 삭제 성공");
        return ResponseEntity.ok().build();
    }
}
