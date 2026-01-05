package org.example.talktown.controller.notice;

import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Member;
import org.example.talktown.domain.Notice;
import org.example.talktown.dto.notice.NoticeRequest;
import org.example.talktown.dto.notice.NoticeResponse;
import org.example.talktown.service.notice.NoticeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeApiController {

    private final NoticeService noticeService;

    //create
    @PostMapping("/api/notices")
    public ResponseEntity<Notice> postNotice(@RequestBody NoticeRequest noticeRequest, @AuthenticationPrincipal Member member){
        noticeRequest.setAuthor(member.getAuthor());
        Notice postNotice = noticeService.save(noticeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postNotice);
    }

    //findAll
    @GetMapping("/api/notices")
    public ResponseEntity<List<NoticeResponse>> getNotices(Pageable pageable){
        List<NoticeResponse> notices = noticeService.findAll(pageable).stream().map(NoticeResponse::new).toList();
        System.out.println("공지사항 글 목록 조회 성공");
        return ResponseEntity.ok().body(notices);
    }

    //findById
    @GetMapping("/api/notices/{id}")
    public ResponseEntity<Notice> getNotice(@PathVariable(value = "id")long id){
        Notice notice = noticeService.findById(id);
        System.out.println(id + "번째 공지사항 글 조회 성공");
        return ResponseEntity.ok().body(notice);
    }

    //delete
    @DeleteMapping("/api/notices/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable(value = "id") long id){
        noticeService.delete(id);
        System.out.println(id + "번째 공지사항 글 삭제 성공");
        return ResponseEntity.ok().build();
    }

    //update
    @PutMapping("/api/notices/{id}")
    public ResponseEntity<Notice> updateNotice(@PathVariable(value = "id") long id, @RequestBody NoticeRequest noticeRequest){
        Notice updateNotice = noticeService.update(id, noticeRequest);
        System.out.println(id + "번째 공지사항 글 수정 성공");
        return ResponseEntity.ok().body(updateNotice);
    }

}
