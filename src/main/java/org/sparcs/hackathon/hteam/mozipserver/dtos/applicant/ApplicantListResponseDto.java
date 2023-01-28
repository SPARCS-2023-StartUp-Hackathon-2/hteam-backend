package org.sparcs.hackathon.hteam.mozipserver.dtos.applicant;

import java.time.LocalDateTime;
import lombok.Getter;
import org.sparcs.hackathon.hteam.mozipserver.entities.Applicant;
import org.sparcs.hackathon.hteam.mozipserver.enums.ApplicantState;

@Getter
public class ApplicantListResponseDto {

    private Long id;
    private String uuid;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime submittedAt;
    private ApplicantState formState;
    private ApplicantState interviewState;

    public ApplicantListResponseDto(Applicant applicant) {
        this.id = applicant.getId();
        this.uuid = applicant.getUuid();
        this.name = applicant.getName();
        this.email = applicant.getEmail();
        this.phoneNumber = applicant.getPhoneNumber();
        this.submittedAt = applicant.getSubmittedAt();
        this.formState = applicant.getFormState();
        this.interviewState = applicant.getInterviewState();
    }
}
