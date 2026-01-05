package org.example.talktown.dto.board;

import lombok.Getter;
import org.example.talktown.domain.Board;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {

    private Long id;
    private String author;
    private String title;
    private String content;
    private int views;
    private LocalDateTime createdAt;

    public BoardResponse(Board board){
        this.id = board.getId();
        this.author = board.getAuthor();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.views = board.getViews();
        this.createdAt = board.getCreatedAt();
    }

}
