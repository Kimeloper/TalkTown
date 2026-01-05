package org.example.talktown.repository;

import org.example.talktown.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Page<Member> findByAuthorContaining(String keyword, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByAuthor(String author);
}
