package org.sparcs.hackathon.hteam.mozipserver.dtos.schedule;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Getter;
import org.sparcs.hackathon.hteam.mozipserver.entities.Schedule;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int interviewerLimit;
    private int intervieweeLimit;
    private int interviewerCount;
    private int intervieweeCount;
    private boolean isRegistered;

    public ScheduleResponseDto(Schedule schedule, String uuid) {
        this.id = schedule.getId();
        this.startAt = schedule.getStartAt();
        this.endAt = schedule.getEndAt();
        this.interviewerLimit = schedule.getInterviewerCount();
        this.intervieweeLimit = schedule.getIntervieweeCount();
        this.interviewerCount = schedule.getInterviewers().size();
        this.intervieweeCount = schedule.getApplicants().size();
        this.isRegistered =
            schedule.getInterviewers().stream()
                .map(scheduleInterviewer -> scheduleInterviewer.getInterviewer().getUuid()).collect(Collectors.toList()).contains(uuid)
            ||
            schedule.getApplicants().stream()
                .map(scheduleApplicant -> scheduleApplicant.getApplicant().getUuid()).collect(Collectors.toList()).contains(uuid);
    }
}
