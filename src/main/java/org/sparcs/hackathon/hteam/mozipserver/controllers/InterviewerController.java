package org.sparcs.hackathon.hteam.mozipserver.controllers;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.dtos.interviewer.InterviewerAddRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.interviewer.InterviewerListResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.entities.Interviewer;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.repositories.InterviewerRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("interviewers")
public class InterviewerController {

    private final InterviewerRepository interviewerRepository;

    @Authorize
    @GetMapping
    List<InterviewerListResponseDto> getAll(@RequestParam Long recruitmentId) {
        return interviewerRepository.findAllByRecruitmentId(recruitmentId).stream()
            .map(InterviewerListResponseDto::new)
            .collect(Collectors.toList());
    }

    @Authorize
    @PostMapping
    void addMultiple(
        @RequestParam Long recruitmentId,
        @RequestBody List<InterviewerAddRequestDto> interviewerAddRequestDtos
    ) {
        List<Interviewer> interviewers = interviewerAddRequestDtos.stream()
            .map(interviewerAddRequestDto -> new Interviewer(
                new Recruitment(recruitmentId),
                interviewerAddRequestDto.getName(),
                interviewerAddRequestDto.getEmail(),
                interviewerAddRequestDto.getPhoneNumber()
            ))
            .collect(Collectors.toList());

        interviewerRepository.saveAll(interviewers);
    }

    @Authorize
    @DeleteMapping
    void deleteMultiple(@RequestParam List<Long> id) {
        interviewerRepository.deleteAllById(id);
    }
}
