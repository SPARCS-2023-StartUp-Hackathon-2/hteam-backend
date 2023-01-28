package org.sparcs.hackathon.hteam.mozipserver.repositories;

import java.util.List;
import java.util.Optional;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    List<Recruitment> findAllByUserId(Long userId);

    Optional<Recruitment> findByUuid(String uuid);
}
