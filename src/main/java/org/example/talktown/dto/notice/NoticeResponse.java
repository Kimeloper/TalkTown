package org.example.talktown.dto.notice;

import lombok.Getter;
import org.example.talktown.domain.Notice;

import java.time.LocalDateTime;

@Getter
public class NoticeResponse {

    private Long id;
    private String author;
    private String title;
    private String content;
    private int views;
    private LocalDateTime createdAt;

    public NoticeResponse(Notice notice){
        this.id = notice.getId();
        this.author = notice.getAuthor();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.views = notice.getViews();
        this.createdAt = notice.getCreatedAt();
    }
}
