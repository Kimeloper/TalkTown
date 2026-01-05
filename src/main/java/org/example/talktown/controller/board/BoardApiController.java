package org.example.talktown.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.talktown.domain.Board;
import org.example.talktown.domain.Member;
import org.example.talktown.dto.board.BoardRequest;
import org.example.talktown.dto.board.BoardResponse;
import org.example.talktown.repository.BoardRepository;
import org.example.talktown.service.board.BoardService;
import org.example.talktown.service.member.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    // Create
    @PostMapping("/api/boards")
    public ResponseEntity<Board> postBoard(@RequestBody BoardRequest boardRequest, @AuthenticationPrincipal Member member) {
        boardRequest.setAuthor(member.getAuthor());
        Board postBoard = boardService.save(boardRequest, member.getAuthor());
        return ResponseEntity.status(HttpStatus.CREATED).body(postBoard);
    }


    // FindAll
    @GetMapping("/api/boards")
    public ResponseEntity<List<BoardResponse>> getBoards(Pageable pageable){
        List<BoardResponse> boards = boardService.findAll(pageable).stream().map(board -> new BoardResponse(board)).toList();
        System.out.println("글 목록 조회 성공");
        return ResponseEntity.ok().body(boards);
    }

    // findById
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable(value = "id") long id){
        Board board = boardService.findById(id);
        System.out.println(id + "번째 글 조회 성공");
        return ResponseEntity.ok().body(board);
    }

    // delete
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable(value = "id") long id){
        boardService.delete(id);
        System.out.println(id + "번째 글 삭제 성공");
        return ResponseEntity.ok().build();
    }

    // update
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable(value = "id") long id, @RequestBody BoardRequest boardRequest){
        Board updateBoard = boardService.update(id, boardRequest);
        System.out.println(id + "번째 게시물 수정 성공");
        return ResponseEntity.ok().body(updateBoard);
    }
}
