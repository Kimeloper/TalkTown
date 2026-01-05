package org.example.talktown.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.talktown.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponse {

    private Long id;
    private String author;
    private String content;

    public CommentResponse(Comment comment){
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
    }

}