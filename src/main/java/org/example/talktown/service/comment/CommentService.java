package org.example.talktown.service.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Board;
import org.example.talktown.domain.Comment;
import org.example.talktown.dto.comment.CommentRequest;
import org.example.talktown.repository.BoardRepository;
import org.example.talktown.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    //save
    public Comment save(CommentRequest commentRequest, String username){
        Board board = boardRepository.findById(commentRequest.getBoardId()).orElseThrow(()-> new IllegalArgumentException("게시글이 존재하지 않습니다. " + commentRequest.getBoardId()));
        return commentRepository.save(commentRequest.toEntity(username, board));
    }

    //delete
    public void delete(Long id){
        commentRepository.deleteById(id);
    }

    //update
    @Transactional
    public Comment update(long id, CommentRequest commentRequest){
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        comment.update(commentRequest.getContent());
        return comment;
    }
}

