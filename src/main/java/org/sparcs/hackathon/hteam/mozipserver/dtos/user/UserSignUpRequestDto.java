package org.sparcs.hackathon.hteam.mozipserver.dtos.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSignUpRequestDto {
    private String username;
    private String password;
    private String groupName;
    private String smtpHost;
    private String smtpEmail;
    private String smtpPassword;
}
