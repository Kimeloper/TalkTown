package org.example.talktown.controller.notice;


import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Notice;

import org.example.talktown.dto.notice.NoticeViewResponse;
import org.example.talktown.service.notice.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeViewController {

    private final NoticeService noticeService;

    @GetMapping("/notices")
    public String getNotices(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "keyword", defaultValue = "")String keyword){

        Page<Notice> notices = null;
        if(keyword == null){
            notices = noticeService.findAll(pageable);
        }else{
            notices = noticeService.searchNotice(keyword, pageable);
        }

        int nowPage = notices.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, notices.getTotalPages());

        model.addAttribute("notices", notices);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "noticeList";
    }

    @GetMapping("/notices/{id}")
    public String getNotice(Model model, @PathVariable(value = "id")long id){
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", new NoticeViewResponse(notice));
        return "notice";
    }

    @GetMapping("/createNotice")
    public String postNotice(Model model){
        model.addAttribute("notice", new NoticeViewResponse());
        return "createNotice";
    }

    @GetMapping("/updateNotice")
    public String updateNotice(Model model, @RequestParam(value = "id")long id){
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", new NoticeViewResponse(notice));
        return "updateNotice";
    }

}
