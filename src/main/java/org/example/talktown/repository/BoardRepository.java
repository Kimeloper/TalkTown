package org.example.talktown.repository;

import org.example.talktown.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

//    @EntityGraph(attributePaths = {"member"})
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    Page<Board> findByAuthorContaining(String author, Pageable pageable);

    Page<Board> findByContentContaining(String keyword, Pageable pageable);
//    @EntityGraph(attributePaths = {"member"})
//    @Query("select b from Board b where b.title like %:keyword%")
//    Page<Board> findByTitleContainingWithMember(@Param("keyword") String keyword, Pageable pageable);

//    @EntityGraph(attributePaths = {"member"})
    Page<Board> findAll(Pageable pageable);

    // 메인페이지용
    List<Board> findTop5ByOrderByCreatedAtDescIdDesc();
}
