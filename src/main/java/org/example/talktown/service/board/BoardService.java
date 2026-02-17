package org.example.talktown.service.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Board;
import org.example.talktown.dto.board.BoardRequest;
import org.example.talktown.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board save(BoardRequest boardRequest, String username){
        return boardRepository.save(boardRequest.toEntity());
    }


    public Page<Board> findAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Board findById(long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("error: " + id + "번째 게시물을 찾을 수 었습니다."));
        board.views();
        return boardRepository.save(board);
    }

    public List<Board> getFiveBoards(){
        return boardRepository.findTop5ByOrderByCreatedAtDescIdDesc();
    }

    public Page<Board> searchBoard(String type, String keyword, Pageable pageable){
        if(type.equals("title")){
            return boardRepository.findByTitleContaining(keyword, pageable);
        }else if(type.equals("author")){
            return boardRepository.findByAuthorContaining(keyword, pageable);
        }else{
            return boardRepository.findAll(pageable);
        }
    }

    public void delete(long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public Board update(long id, BoardRequest boardRequest){
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("error: " + id + "번째 게시물을 찾을 수 었습니다."));
        board.update(boardRequest.getTitle(), boardRequest.getContent());
        return board;
    }

}
