package org.sparcs.hackathon.hteam.mozipserver.repositories;

import java.util.Optional;
import org.sparcs.hackathon.hteam.mozipserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
