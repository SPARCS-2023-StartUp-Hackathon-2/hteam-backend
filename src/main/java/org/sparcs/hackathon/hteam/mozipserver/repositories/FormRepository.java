package org.sparcs.hackathon.hteam.mozipserver.repositories;

import java.util.Optional;
import org.sparcs.hackathon.hteam.mozipserver.entities.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    Optional<Form> findByRecruitmentId(Long recruitmentId);
}
