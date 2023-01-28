package org.sparcs.hackathon.hteam.mozipserver.controllers;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.dtos.applicant.ApplicantApplyRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.applicant.ApplicantFormResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.applicant.ApplicantListResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.applicant.ApplicantResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.applicant.ApplicantUpdateStateRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.entities.Applicant;
import org.sparcs.hackathon.hteam.mozipserver.entities.Form;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.enums.RecruitmentState;
import org.sparcs.hackathon.hteam.mozipserver.repositories.ApplicantRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.FormRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.RecruitmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("applicants")
public class ApplicantController {

    private final ApplicantRepository applicantRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final FormRepository formRepository;

    @Authorize
    @GetMapping
    List<ApplicantListResponseDto> getAll(@RequestParam Long recruitmentId) {
        return applicantRepository.findAllByRecruitmentId(recruitmentId)
            .stream().map(ApplicantListResponseDto::new)
            .collect(Collectors.toList());
    }

    @Authorize
    @GetMapping("{id}")
    ApplicantResponseDto getById(@PathVariable Long id) {
        Applicant applicant = applicantRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ApplicantResponseDto(applicant);
    }

    @GetMapping("application-form/{uuid}")
    ApplicantFormResponseDto getApplicantForm(@PathVariable String uuid) {
        Recruitment recruitment = recruitmentRepository.findByUuid(uuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Form form = formRepository.findByRecruitmentId(recruitment.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ApplicantFormResponseDto(form.getContent());
    }

    @PostMapping("apply/{uuid}")
    void apply(
        @PathVariable String uuid,
        @RequestBody ApplicantApplyRequestDto applicantApplyRequestDto
    ) {
        Recruitment recruitment = recruitmentRepository.findByUuid(uuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // TODO: 이미 지원한지 여부 검사

        Applicant applicant = new Applicant(
            recruitment,
            applicantApplyRequestDto.getName(),
            applicantApplyRequestDto.getEmail(),
            applicantApplyRequestDto.getPhoneNumber(),
            applicantApplyRequestDto.getContent()
        );

        applicantRepository.save(applicant);
    }

    @Authorize
    @PatchMapping("{id}/state")
    void updateState(
        @PathVariable Long id,
        @RequestBody ApplicantUpdateStateRequestDto applicantUpdateStateRequestDto
    ) {
        Applicant applicant = applicantRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (applicantUpdateStateRequestDto.getType().equals(RecruitmentState.FORM)) {
            applicant.setFormState(applicantUpdateStateRequestDto.getState());
        } else if (applicantUpdateStateRequestDto.getType().equals(RecruitmentState.INTERVIEW)) {
            applicant.setInterviewState(applicantUpdateStateRequestDto.getState());
        }

        applicantRepository.save(applicant);
    }
}
