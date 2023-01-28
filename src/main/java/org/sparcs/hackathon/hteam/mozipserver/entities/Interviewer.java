package org.sparcs.hackathon.hteam.mozipserver.entities;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Interviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Recruitment recruitment;

    private String name;
    private String email;
    private String phoneNumber;

    public Interviewer(Recruitment recruitment, String name, String email, String phoneNumber) {
        this.recruitment = recruitment;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uuid = UUID.randomUUID().toString();
    }
}
