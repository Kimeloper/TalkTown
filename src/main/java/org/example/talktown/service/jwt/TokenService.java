package org.example.talktown.service.jwt;

import lombok.RequiredArgsConstructor;
import org.example.talktown.config.jwt.JwtUtil;
import org.example.talktown.domain.Member;
import org.example.talktown.service.member.MemberService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) {
        if (!jwtUtil.validToken(refreshToken)) {
            throw new IllegalStateException("Unexpected token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberService.findById(memberId);

        return jwtUtil.generateToken(member, Duration.ofDays(2));
    }
}
