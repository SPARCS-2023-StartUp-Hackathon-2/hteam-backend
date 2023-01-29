package org.sparcs.hackathon.hteam.mozipserver.repositories;

import java.util.List;
import java.util.Optional;
import org.sparcs.hackathon.hteam.mozipserver.entities.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

    List<Interviewer> findAllByRecruitmentId(Long recruitmentId);

    Optional<Interviewer> findByUuid(String uuid);
}
