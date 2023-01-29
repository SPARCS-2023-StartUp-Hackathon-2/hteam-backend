package org.sparcs.hackathon.hteam.mozipserver.repositories;

import org.sparcs.hackathon.hteam.mozipserver.entities.ScheduleApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleApplicantRepository extends JpaRepository<ScheduleApplicant, Long> {

}
