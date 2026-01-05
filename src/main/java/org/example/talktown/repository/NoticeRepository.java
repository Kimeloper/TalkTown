package org.example.talktown.repository;

import org.example.talktown.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findByTitleContaining(String keyword, Pageable pageable);

    //메인페이지용
    List<Notice> findTop5ByOrderByCreatedAtDescIdDesc();
}
