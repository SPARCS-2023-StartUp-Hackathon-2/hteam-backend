package org.sparcs.hackathon.hteam.mozipserver.repositories;

import java.util.List;
import org.sparcs.hackathon.hteam.mozipserver.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByRecruitmentId(Long recruitmentId);
}
