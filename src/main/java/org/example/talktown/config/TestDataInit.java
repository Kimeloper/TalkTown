package org.example.talktown.config;

import jakarta.annotation.PostConstruct;
import org.example.talktown.domain.Board;
import org.example.talktown.domain.Member;
import org.example.talktown.domain.Role;

import org.example.talktown.repository.BoardRepository;
import org.example.talktown.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
//public class TestDataInit  {
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @PostConstruct
//    public void init() {
//        // Member 생성
//        Member member = new Member();
//        member.setAuthor("글쓴이");
//        memberRepository.save(member);
//
//        // Board 10개 생성
//        for (int i = 0; i < 2000; i++) {
//            Board board = new Board();
//            board.setTitle("board " + i);
//            board.setContent("Content" + i);
//            board.setViews(0);
//            board.setAuthor(member.getAuthor());
//            board.setMember(member);
//            boardRepository.save(board);
//        }
//    }
//
//}
