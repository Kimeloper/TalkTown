package org.example.talktown.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "members")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(unique = true)
    private String author;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @CreatedDate
    @Column
    private LocalDateTime registeredAt;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role; // MEMBER, ADMIN


    @Builder
    public Member(String author, String email, String password, Role role, LocalDateTime registeredAt){
        this.author = author;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registeredAt = registeredAt;
    }

    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }


    //사용자의 id를 반환
    @Override
    public String getUsername(){
        return author;
    }

    //사용자의 패스워드를 반환
    @Override
    public String getPassword(){
        return password;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired(){
        //만료되었는지 확인하는 로직
        return true;
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked(){
        //계정 잠금이 되었는지 확인하는 로직
        return true;
    }

    //패스워드의 만료 여부반환
    @Override
    public boolean isCredentialsNonExpired(){
        //패스워드가 만료되었는지 확인하는 로직
        return true;
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled(){
        //계정이 사용가능한지 확인하는 로직
        return true;
    }

}
