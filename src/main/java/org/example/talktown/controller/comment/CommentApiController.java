package org.example.talktown.controller.comment;

import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Comment;
import org.example.talktown.domain.Member;
import org.example.talktown.dto.comment.CommentRequest;
import org.example.talktown.dto.comment.CommentResponse;
import org.example.talktown.service.board.BoardService;
import org.example.talktown.service.comment.CommentService;
import org.example.talktown.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentService commentService;

    //post
    @PostMapping("/api/comments")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest, Authentication authentication){
        Member member = (Member) authentication.getPrincipal();
        Comment savedComment = commentService.save(commentRequest, member.getAuthor());
        System.out.println("댓글 작성 성공!");
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponse(savedComment));
    }

    //delete
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "id") long id){
        commentService.delete(id);
        System.out.println(id + "번째 댓글 삭제 성공!");
        return ResponseEntity.ok().build();
    }

    //update
    @PutMapping("/api/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(value = "id") long id, @RequestBody CommentRequest commentRequest) {
        Comment updated = commentService.update(id, commentRequest);
        System.out.println(id + "번째 댓글 수정 성공!");
        return ResponseEntity.ok(updated);
    }

}
