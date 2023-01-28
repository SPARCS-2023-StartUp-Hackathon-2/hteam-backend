package org.sparcs.hackathon.hteam.mozipserver.dtos.interviewer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewerAddRequestDto {

    private String name;
    private String email;
    private String phoneNumber;
}
