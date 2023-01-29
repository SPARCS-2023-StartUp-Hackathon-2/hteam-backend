package org.sparcs.hackathon.hteam.mozipserver.repositories;

import java.util.List;
import java.util.Optional;
import org.sparcs.hackathon.hteam.mozipserver.entities.Applicant;
import org.sparcs.hackathon.hteam.mozipserver.enums.ApplicantState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    List<Applicant> findAllByRecruitmentId(Long recruitmentId);
    List<Applicant> findAllByRecruitmentIdAndFormState(Long recruitmentId, ApplicantState formState);

    Optional<Applicant> findByUuid(String uuid);
}
