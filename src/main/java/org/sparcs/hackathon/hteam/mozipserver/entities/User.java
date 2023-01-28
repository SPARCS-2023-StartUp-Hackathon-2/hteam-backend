package org.sparcs.hackathon.hteam.mozipserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String groupName;
//    private String groupType;
    private String smtpHost;
    private String smtpEmail;
    private String smtpPassword;

    public User(
        String username,
        String password,
        String groupName,
        String smtpHost,
        String smtpEmail,
        String smtpPassword
    ) {
        this.username = username;
        this.password = password;
        this.groupName = groupName;
        this.smtpHost = smtpHost;
        this.smtpEmail = smtpEmail;
        this.smtpPassword = smtpPassword;
    }
}
