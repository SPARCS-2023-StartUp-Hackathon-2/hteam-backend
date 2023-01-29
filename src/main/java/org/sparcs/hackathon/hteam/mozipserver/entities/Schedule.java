package org.sparcs.hackathon.hteam.mozipserver.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Recruitment recruitment;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int interviewerCount;
    private int intervieweeCount;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<ScheduleInterviewer> interviewers;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<ScheduleApplicant> applicants;

    public Schedule(
        Recruitment recruitment,
        LocalDateTime startAt,
        LocalDateTime endAt,
        int interviewerCount,
        int intervieweeCount
    ) {
        this.recruitment = recruitment;
        this.startAt = startAt;
        this.endAt = endAt;
        this.interviewerCount = interviewerCount;
        this.intervieweeCount = intervieweeCount;
    }
}
