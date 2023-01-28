package org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment;

import java.time.LocalDateTime;
import lombok.Getter;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.enums.RecruitmentState;

@Getter
public class RecruitmentListResponseDto {

    private Long id;
    private String name;
    private RecruitmentState state;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public RecruitmentListResponseDto(Recruitment recruitment) {
        this.id = recruitment.getId();
        this.name = recruitment.getName();
        this.state = recruitment.getState();
        this.startAt = recruitment.getStartAt();
        this.endAt = recruitment.getEndAt();
    }
}
