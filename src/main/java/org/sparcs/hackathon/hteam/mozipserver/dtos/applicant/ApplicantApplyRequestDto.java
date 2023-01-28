package org.sparcs.hackathon.hteam.mozipserver.dtos.applicant;

import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicantApplyRequestDto {

    private String name;
    private String email;
    private String phoneNumber;
    private Map<String, Object> content;
}
