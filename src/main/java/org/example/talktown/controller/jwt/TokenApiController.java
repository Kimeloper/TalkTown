package org.example.talktown.controller.jwt;

import lombok.RequiredArgsConstructor;
import org.example.talktown.dto.jwt.CreateAccessTokenRequest;
import org.example.talktown.dto.jwt.CreateAccessTokenResponse;
import org.example.talktown.service.jwt.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest createAccessTokenRequest){
        String newAccessToken = tokenService.createNewAccessToken(createAccessTokenRequest.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }

}
