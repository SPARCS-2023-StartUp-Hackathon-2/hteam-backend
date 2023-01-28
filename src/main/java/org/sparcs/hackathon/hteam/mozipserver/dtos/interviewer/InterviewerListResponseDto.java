package org.sparcs.hackathon.hteam.mozipserver.dtos.interviewer;

import lombok.Getter;
import org.sparcs.hackathon.hteam.mozipserver.entities.Interviewer;

@Getter
public class InterviewerListResponseDto {

    private Long id;
    private String uuid;
    private String name;
    private String email;
    private String phoneNumber;

    public InterviewerListResponseDto(Interviewer interviewer) {
        this.id = interviewer.getId();
        this.uuid = interviewer.getUuid();
        this.name = interviewer.getName();
        this.email = interviewer.getEmail();
        this.phoneNumber = interviewer.getPhoneNumber();
    }
}
