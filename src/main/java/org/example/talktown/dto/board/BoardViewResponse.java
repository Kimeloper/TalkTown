package org.example.talktown.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.talktown.domain.Board;
import org.example.talktown.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class BoardViewResponse {

    private Long id;
    private String author;
    private String title;
    private String content;
    private int views;
    private LocalDateTime createdAt;

    private List<Comment> comments;

    public BoardViewResponse(Board board){
        this.id = board.getId();
        this.author = board.getAuthor();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.views = board.getViews();
        this.createdAt = board.getCreatedAt();
        this.comments = board.getComments();
    }
}
