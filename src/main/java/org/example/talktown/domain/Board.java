package org.example.talktown.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "boards")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int views;

    @CreatedDate
    @Column(name = "created_at" , nullable = false)
    private LocalDateTime createdAt;

    // 댓글 N: 회원 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member member;


    @Builder
    public Board(String author, String title, String content, int views, Member member){
        this.author = author;
        this.title = title;
        this.content = content;
        this.views = views;
        this.member = member;
    }

    // 수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    // 조회수
    public void views(){
        this.views++;
    }

    //댓글 ( 게시물 1 : 댓글 N)
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

}
