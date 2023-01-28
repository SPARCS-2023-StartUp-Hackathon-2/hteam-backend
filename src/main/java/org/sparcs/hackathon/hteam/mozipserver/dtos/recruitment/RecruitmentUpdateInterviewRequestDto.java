package org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparcs.hackathon.hteam.mozipserver.enums.InterviewType;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitmentUpdateInterviewRequestDto {

    private InterviewType type;
    private String notice;
}
