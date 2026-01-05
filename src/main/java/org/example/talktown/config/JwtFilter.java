package org.example.talktown.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.talktown.config.jwt.JwtUtil;
import org.example.talktown.domain.Member;
import org.example.talktown.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public JwtFilter(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if ("accessToken".equals(cookies[i].getName())) {
                    token = cookies[i].getValue();
                    break;
                }
            }
        }

        if (token != null && jwtUtil.validToken(token)) {
            String email = jwtUtil.getClaims(token).getSubject();
            Member member = memberRepository.findByEmail(email).orElse(null);

            if (member != null) {
                Authentication auth = new UsernamePasswordAuthenticationToken(member, null, List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole())));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
