package org.example.talktown.controller.board;

import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Board;
import org.example.talktown.dto.board.BoardViewResponse;
import org.example.talktown.service.board.BoardService;
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
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String getBoards(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam(value = "type", defaultValue = "title") String type){

        Page<Board> boards = null;

        if(keyword == null || keyword.isBlank()){ // 검색 안했을때
            boards = boardService.findAll(pageable);
        }else{ // 검색 했을때
            boards = boardService.searchBoard(type,keyword, pageable);
        }

        int nowPage = boards.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, boards.getTotalPages());

        model.addAttribute("boards", boards);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardList";
    }

    @GetMapping("/boards/{id}")
    public String getBoard(Model model, @PathVariable(value = "id")long id){
        Board board = boardService.findById(id);
        model.addAttribute("board", new BoardViewResponse(board));
        return "board";
    }

    @GetMapping("/createBoard")
    public String postBoard(Model model){
        model.addAttribute("board", new BoardViewResponse());
        return "createBoard";
    }

    @GetMapping("/updateBoard")
    public String updateBoard(Model model, @RequestParam(value = "id")long id){
        Board board = boardService.findById(id);
        model.addAttribute("board", new BoardViewResponse(board));
        return "updateBoard";
    }

}
