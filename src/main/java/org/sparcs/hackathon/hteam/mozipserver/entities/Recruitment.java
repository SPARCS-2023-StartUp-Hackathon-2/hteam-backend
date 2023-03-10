package org.sparcs.hackathon.hteam.mozipserver.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.sparcs.hackathon.hteam.mozipserver.enums.InterviewType;
import org.sparcs.hackathon.hteam.mozipserver.enums.RecruitmentState;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(unique = true)
    private String uuid;

    private String name;

    @Enumerated(EnumType.STRING)
    private RecruitmentState state;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    private String interviewNotice;
    private Integer interviewerCount;
    private Integer intervieweeCount;

    public Recruitment(User user, String name, LocalDateTime startAt, LocalDateTime endAt) {
        this.user = user;
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.state = RecruitmentState.PREPARING;
        this.uuid = UUID.randomUUID().toString();
    }

    public Recruitment(Long id) {
        this.id = id;
    }
}
