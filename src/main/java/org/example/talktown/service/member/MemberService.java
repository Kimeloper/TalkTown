package org.example.talktown.service.member;

import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Member;

import org.example.talktown.domain.Role;
import org.example.talktown.dto.member.MemberRequest;
import org.example.talktown.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(MemberRequest memberRequest){
        /*클라이언트가 보낸 회원가입 데이터를 받아서 비밀번호를 암호화한 후 DB에 저장하고,
        새로 만들어진 회원의 id를 반환하는 코드

        ※그럼 왜 id를 반환할까? 여기서 id는 primary key 즉 고유값으로, 유일한 식별자 역할을 하기 때문이다.
          @Id <- 이 어노테이션이 PK를 의미하는데, 나는 id에 이 어노테이션을 붙여서 id가 PK가 되었다.*/

        return memberRepository.save(Member.builder()
                .email(memberRequest.getEmail())
                .author(memberRequest.getAuthor())
                .password(bCryptPasswordEncoder.encode(memberRequest.getPassword()))
                .role(Role.MEMBER)
                .build()).getId();
    }

    public Boolean checkEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    public Boolean checkAuthor(String author){
        return memberRepository.existsByAuthor(author);
    }

    //회원 탈퇴
    public boolean deleteMemberIfPasswordIsCorrect(Member member, String password){
        if(bCryptPasswordEncoder.matches(password, member.getPassword())){
            memberRepository.delete(member);
            return true;
        }else{
            return false;
        }
    }
    
    // 멤버 검색
    public Page<Member> findAllMembers(Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    public Page<Member> searchMember(String keyword, Pageable pageable){
        return memberRepository.findByAuthorContaining(keyword, pageable);
    }
    
    //JWT 메서드
    public Member findById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("Unexpected member"));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("email을 찾을 수 없습니다."));
    }
}
