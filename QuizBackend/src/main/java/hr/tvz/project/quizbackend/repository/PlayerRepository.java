package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerDB, Long> {

    Optional<PlayerDB> findByUsername(String username);
    Optional<PlayerDB> findByUuid(String uuid);
    Optional<PlayerDB> findByUsernameAndHashedPassword(String username, String hPassword);

}
