package org.sparcs.hackathon.hteam.mozipserver.repositories;

import org.sparcs.hackathon.hteam.mozipserver.entities.ScheduleInterviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleInterviewerRepository extends JpaRepository<ScheduleInterviewer, Long> {

}
