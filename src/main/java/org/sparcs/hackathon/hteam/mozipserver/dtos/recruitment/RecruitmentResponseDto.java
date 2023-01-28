package org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment;

import java.time.LocalDateTime;
import lombok.Getter;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.enums.InterviewType;
import org.sparcs.hackathon.hteam.mozipserver.enums.RecruitmentState;

@Getter
public class RecruitmentResponseDto {

    private Long id;
    private String uuid;
    private String name;
    private RecruitmentState state;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private InterviewType interviewType;
    private String interviewNotice;
    private LocalDateTime createdAt;

    public RecruitmentResponseDto(Recruitment recruitment) {
        this.id = recruitment.getId();
        this.uuid = recruitment.getUuid();
        this.name = recruitment.getName();
        this.state = recruitment.getState();
        this.startAt = recruitment.getStartAt();
        this.endAt = recruitment.getEndAt();
        this.interviewType = recruitment.getInterviewType();
        this.interviewNotice = recruitment.getInterviewNotice();
        this.createdAt = recruitment.getCreatedAt();
    }
}
