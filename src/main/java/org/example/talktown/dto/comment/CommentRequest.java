package org.example.talktown.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.talktown.domain.Board;
import org.example.talktown.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentRequest {

    private Long boardId;// 어느 게시글에 댓글을 다는지
    private String author;// 누가 댓글을 달았는지
    private String content;// 어떤 내용을 담았는지

    public Comment toEntity(String username, Board board){
        return Comment.builder()
                .board(board) // 부모 엔티티(Board) 매핑
                .author(username) // 자동 입력되는 작성자
                .content(content) // 사용자가 입력한 댓글 내용
                .build();
    }
}
