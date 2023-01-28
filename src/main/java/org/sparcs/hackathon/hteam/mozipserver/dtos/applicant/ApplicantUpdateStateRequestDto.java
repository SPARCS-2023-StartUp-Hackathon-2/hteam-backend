package org.sparcs.hackathon.hteam.mozipserver.dtos.applicant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparcs.hackathon.hteam.mozipserver.enums.ApplicantState;
import org.sparcs.hackathon.hteam.mozipserver.enums.RecruitmentState;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicantUpdateStateRequestDto {

    private RecruitmentState type;
    private ApplicantState state;
}
