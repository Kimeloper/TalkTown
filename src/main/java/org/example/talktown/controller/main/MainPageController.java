package org.example.talktown.controller.main;

import lombok.RequiredArgsConstructor;
import org.example.talktown.service.board.BoardService;
import org.example.talktown.service.notice.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final BoardService boardService;
    private final NoticeService noticeService;

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("boards", boardService.getFiveBoards());
        model.addAttribute("notices", noticeService.getFiveBoards());
        return "main";
    }

}
