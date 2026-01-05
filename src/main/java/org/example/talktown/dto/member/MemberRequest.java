package org.example.talktown.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,8}$")
    private String author;

    @NotBlank
    @Size(min=6, max=12)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{6,12}$")
    private String password;
}
