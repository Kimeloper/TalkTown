package org.example.talktown.dto.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.talktown.domain.Notice;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeViewResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private int views;
    private LocalDateTime createdAt;

    public NoticeViewResponse(Notice notice){
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.author = notice.getAuthor();
        this.views = notice.getViews();
        this.createdAt = notice.getCreatedAt();
    }
}
