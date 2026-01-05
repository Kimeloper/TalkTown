package org.example.talktown.dto.jwt;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccessTokenRequest {//클라이언트 -> 서버

    private String refreshToken;

}
