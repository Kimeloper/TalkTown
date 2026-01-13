package org.example.talktown.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.talktown.domain.Board;

@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 피라미터로 받는 생성자 추가
@Getter
@Setter
public class BoardRequest {

    private String author;
    private String title;
    private String content;

    public Board toEntity(){ // 생성자를 사용해 객체 완성
        return Board.builder()
                .author(author)
                .title(title)
                .content(content)
                .views(0) // 0부터 시작
                .build();
    }

    public void setAuthor(String username) {
        this.author = username;
    }
}
