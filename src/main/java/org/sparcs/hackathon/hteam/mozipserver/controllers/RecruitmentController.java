package org.sparcs.hackathon.hteam.mozipserver.controllers;

import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.CurrentUser;
import org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment.RecruitmentResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment.RecruitmentUpdateInterviewRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment.RecruitmentUpdateStateRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment.RecruitmentCreateRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.recruitment.RecruitmentListResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.entities.User;
import org.sparcs.hackathon.hteam.mozipserver.repositories.RecruitmentRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.UserRespository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("recruitments")
public class RecruitmentController {

    private final RecruitmentRepository recruitmentRepository;
    private final UserRespository userRespository;

    @Authorize
    @GetMapping
    Stream<RecruitmentListResponseDto> getAll(@CurrentUser Long userId) {
        return recruitmentRepository.findAllByUserId(userId).stream()
            .map(RecruitmentListResponseDto::new);
    }

    @Authorize
    @GetMapping("{id}")
    RecruitmentResponseDto getById(@PathVariable Long id) {
        Recruitment recruitment = recruitmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new RecruitmentResponseDto(recruitment);
    }

    @Authorize
    @PostMapping
    void create(
        @CurrentUser Long userId,
        @RequestBody RecruitmentCreateRequestDto recruitmentCreateRequestDto
    ) {
        User user = userRespository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Recruitment recruitment = new Recruitment(
            user,
            recruitmentCreateRequestDto.getName(),
            recruitmentCreateRequestDto.getStartAt(),
            recruitmentCreateRequestDto.getEndAt()
        );

        recruitmentRepository.save(recruitment);
    }

    @Authorize
    @PatchMapping("{id}/state")
    void updateState(
        @CurrentUser Long userId,
        @PathVariable Long id,
        @RequestBody RecruitmentUpdateStateRequestDto recruitmentUpdateStateRequestDto
    ) {
        Recruitment recruitment = recruitmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        recruitment.setState(recruitmentUpdateStateRequestDto.getState());

        recruitmentRepository.save(recruitment);
    }

    @Authorize
    @PatchMapping("{id}/interview")
    void updateInterview(
        @CurrentUser Long userId,
        @PathVariable Long id,
        @RequestBody RecruitmentUpdateInterviewRequestDto recruitmentUpdateInterviewRequestDto
    ) {
        Recruitment recruitment = recruitmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // TODO: state 검증

        recruitment.setInterviewType(recruitmentUpdateInterviewRequestDto.getType());
        recruitment.setInterviewNotice(recruitmentUpdateInterviewRequestDto.getNotice());

        recruitmentRepository.save(recruitment);
    }
}
