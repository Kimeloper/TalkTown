package org.example.talktown.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.talktown.config.jwt.JwtUtil;
import org.example.talktown.repository.MemberRepository;
import org.example.talktown.service.member.MemberDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberDetailService memberService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/img/**")
                .requestMatchers("/js/**")
                .requestMatchers("/css/**")
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/members", "/api/login",  "/api/logout", "/login", "/signup", "/api/nickname","/member", "/main", "/boards", "/notices", "/checkEmail",  "/checkAuthor").permitAll()
                        .requestMatchers("/adminPage", "/newNotice", "/api/notices","/admin").hasRole("ADMIN")
                        .requestMatchers("/api/newBoard").hasAnyRole("ADMIN", "MEMBER")
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(jwtUtil, memberRepository),UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {

                            String uri = request.getRequestURI();

                            if(uri.startsWith("/api")) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType("application/json; charset=UTF-8");
                                response.getWriter().write("{\"status\":\"401\",\"error\":\"Unauthorized\", \"message\":\"로그인을 해주세요.\"}");
                            }else {
                                response.sendRedirect("/login");
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, MemberDetailService memberDetailService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(memberService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
