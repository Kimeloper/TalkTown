package org.example.talktown.service.member;

import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Member;
import org.example.talktown.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//스프링 시큐리티에서 사용자의 정보를 가져오는 인터페이스
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    
    //사용자 이름(email)로 사용자의 정보를 가져오는 메서드
    @Override
    public Member loadUserByUsername(String email){
        return memberRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException((email)));
    }
}
