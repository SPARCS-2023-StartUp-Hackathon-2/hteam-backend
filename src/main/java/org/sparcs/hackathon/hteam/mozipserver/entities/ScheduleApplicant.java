package org.sparcs.hackathon.hteam.mozipserver.entities;

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
public class ScheduleApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Applicant applicant;

    public ScheduleApplicant(Schedule schedule, Applicant applicant) {
        this.schedule = schedule;
        this.applicant = applicant;
    }
}
