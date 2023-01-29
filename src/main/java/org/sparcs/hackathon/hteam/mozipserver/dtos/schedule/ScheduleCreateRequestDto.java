package org.sparcs.hackathon.hteam.mozipserver.dtos.schedule;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleCreateRequestDto {

    private String date;
    private List<String> times;
}
