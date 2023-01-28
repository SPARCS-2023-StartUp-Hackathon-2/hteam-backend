package org.sparcs.hackathon.hteam.mozipserver.dtos;

import lombok.Getter;
import org.sparcs.hackathon.hteam.mozipserver.entities.User;

@Getter
public class UserMeResponseDto {

    private String username;
    private String groupName;
    private String smtpHost;
    private String smtpEmail;

    public UserMeResponseDto(User user) {
        this.username = user.getUsername();
        this.groupName = user.getGroupName();
        this.smtpHost = user.getSmtpHost();
        this.smtpEmail = user.getSmtpEmail();
    }
}
