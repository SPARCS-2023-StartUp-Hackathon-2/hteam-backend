package org.sparcs.hackathon.hteam.mozipserver.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.dtos.schedule.ScheduleCreateRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.schedule.ScheduleResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.entities.Schedule;
import org.sparcs.hackathon.hteam.mozipserver.repositories.RecruitmentRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("schedules")
public class ScheduleController {

    private final RecruitmentRepository recruitmentRepository;
    private final ScheduleRepository scheduleRepository;

    @Authorize
    @PostMapping
    void createAll(
        @RequestParam Long recruitmentId,
        @RequestBody List<ScheduleCreateRequestDto> scheduleCreateRequestDtos
    ) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Schedule> schedules = scheduleCreateRequestDtos.stream()
            .flatMap(scheduleCreateRequestDto -> convertToSchedule(recruitment, scheduleCreateRequestDto))
            .collect(Collectors.toList());

        scheduleRepository.saveAll(schedules);
    }

    private Stream<Schedule> convertToSchedule(Recruitment recruitment, ScheduleCreateRequestDto scheduleCreateRequestDto) {
        LocalDate date = LocalDate.parse(scheduleCreateRequestDto.getDate(), DateTimeFormatter.ISO_DATE);
        return scheduleCreateRequestDto.getTimes().stream()
            .map(time -> {
                String[] times = time.split("-");
                String[] startTime = times[0].split(":");
                String[] endTime = times[1].split(":");
                LocalDateTime startAt = date.atTime(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]));
                LocalDateTime endAt = date.atTime(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]));

                return new Schedule(
                    recruitment,
                    startAt,
                    endAt,
                    recruitment.getInterviewerCount(),
                    recruitment.getIntervieweeCount()
                );
            });
    }

    @GetMapping("{uuid}")
    List<ScheduleResponseDto> getAll(
        @PathVariable String uuid,
        @RequestParam Long recruitmentId
    ) {
        List<Schedule> schedules = scheduleRepository.findAllByRecruitmentId(recruitmentId);

        return schedules.stream()
            .map(schedule -> new ScheduleResponseDto(schedule, uuid))
            .collect(Collectors.toList());
    }
}
