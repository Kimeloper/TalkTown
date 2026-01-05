package org.example.talktown.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.talktown.domain.Notice;

@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 피라미터로 받는 생성자 추가
@Getter
public class NoticeRequest {

    private String author;
    private String title;
    private String content;


    public Notice toEntity(){
        return Notice.builder()
                .title(title)
                .content(content)
                .author(author)
                .views(0) // 0 부터 시작
                .build();
    }

    public void setAuthor(String username) {
        this.author = username;
    }
}
