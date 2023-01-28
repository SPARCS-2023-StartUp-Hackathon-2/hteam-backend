package org.sparcs.hackathon.hteam.mozipserver.repositories;

import java.util.List;
import org.sparcs.hackathon.hteam.mozipserver.entities.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    List<Applicant> findAllByRecruitmentId(Long recruitmentId);
}
