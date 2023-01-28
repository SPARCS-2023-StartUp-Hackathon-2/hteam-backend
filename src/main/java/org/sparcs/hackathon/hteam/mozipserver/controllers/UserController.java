package org.sparcs.hackathon.hteam.mozipserver.controllers;

import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.CurrentUser;
import org.sparcs.hackathon.hteam.mozipserver.dtos.user.UserMeResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.user.UserSignInRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.user.UserSignUpRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.entities.User;
import org.sparcs.hackathon.hteam.mozipserver.repositories.UserRespository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private final Key key;
    private final PasswordEncoder passwordEncoder;
    private final UserRespository userRespository;

    @PostMapping("signin")
    String signin(@RequestBody UserSignInRequestDto userSignInRequestDto) {
        // username으로 user 조회
        User user = userRespository.findByUsername(userSignInRequestDto.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // password 해싱 검증
        if (!passwordEncoder.matches(userSignInRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // id로 JWT 생성 및 반환
        LocalDateTime now = LocalDateTime.now();
        return Jwts.builder()
            .setIssuedAt(Timestamp.valueOf(now))
            .setExpiration(Timestamp.valueOf(now.plusDays(3)))
            .addClaims(Map.of("id", user.getId()))
            .signWith(key)
            .compact();
    }

    @Authorize
    @GetMapping("me")
    UserMeResponseDto getMe(@CurrentUser Long userId) {
        User user = userRespository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new UserMeResponseDto(user);
    }

    @PostMapping("signup")
    void signup(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        User user = new User(
            userSignUpRequestDto.getUsername(),
            passwordEncoder.encode(userSignUpRequestDto.getPassword()),
            userSignUpRequestDto.getGroupName(),
            userSignUpRequestDto.getSmtpHost(),
            userSignUpRequestDto.getSmtpEmail(),
            userSignUpRequestDto.getSmtpPassword());
        userRespository.save(user);
    }
}
