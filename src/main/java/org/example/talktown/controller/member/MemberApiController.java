package org.example.talktown.controller.member;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.talktown.config.jwt.JwtUtil;
import org.example.talktown.domain.Member;
import org.example.talktown.domain.RefreshToken;
import org.example.talktown.dto.login.LoginRequest;
import org.example.talktown.dto.member.MemberRequest;
import org.example.talktown.repository.MemberRepository;
import org.example.talktown.repository.RefreshTokenRepository;
import org.example.talktown.service.member.MemberService;
import org.example.talktown.service.member.NicknameService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RequiredArgsConstructor
@Controller
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final NicknameService nicknameService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/api/login")
    public String login(LoginRequest loginRequest,HttpServletResponse httpServletResponse) {

        Member member = memberService.findByEmail(loginRequest.getEmail());

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(),member.getPassword()) || member == null) {
            return "redirect:/login?error";
        }

        String accessToken = jwtUtil.generateToken(member, Duration.ofHours(1));
        String refreshToken = jwtUtil.generateToken(member, Duration.ofDays(14));

        refreshTokenRepository.save(new RefreshToken(member.getId(), refreshToken));

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60)
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(14 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return "redirect:/main";
    }

    @PostMapping("/api/logout")
    public String logout(HttpServletResponse httpServletResponse) {

        // 쿠키 삭제
        ResponseCookie deleteAccess = ResponseCookie.from("accessToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .sameSite("Lax")
                .build();

        ResponseCookie deleteRefresh = ResponseCookie.from("refreshToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .sameSite("Lax")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, deleteAccess.toString());
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, deleteRefresh.toString());

        return "redirect:/main";
    }

    @PostMapping("/member")
    public String signup(@Valid @ModelAttribute MemberRequest memberRequest, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("memberRequest", memberRequest);
            return "signup";
        }

        boolean checkAuthor = memberService.checkAuthor(memberRequest.getAuthor());
        boolean checkEmail = memberService.checkEmail(memberRequest.getEmail());

        if (checkAuthor == true || checkEmail == true) {
            return "signup";
        }

        memberService.save(memberRequest);
        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping("/api/nickname")
    public String getRandomNickname() {
        return nicknameService.randomNickname();
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam(value = "email") String email) {
        boolean checkEmail = memberService.checkEmail(email);
        return ResponseEntity.ok(checkEmail);
    }

    @GetMapping("/checkAuthor")
    public ResponseEntity<Boolean> checkAuthor(@RequestParam(value = "author") String author) {
        boolean checkAuthor = memberService.checkAuthor(author);
        return ResponseEntity.ok(checkAuthor);
    }


    @PostMapping("/deleteMember")
    public ResponseEntity<Void> deleteMember(@RequestParam("password") String password, @AuthenticationPrincipal Member member, HttpServletResponse httpServletResponse) {

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        memberRepository.delete(member);

        ResponseCookie deleteAccess = ResponseCookie.from("accessToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .sameSite("Lax")
                .build();

        ResponseCookie deleteRefresh = ResponseCookie.from("refreshToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .sameSite("Lax")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, deleteAccess.toString());
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, deleteRefresh.toString());

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().build();
    }

}
