package org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparcs.hackathon.hteam.mozipserver.enums.RecruitmentState;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitmentUpdateStateRequestDto {

    private RecruitmentState state;
}
