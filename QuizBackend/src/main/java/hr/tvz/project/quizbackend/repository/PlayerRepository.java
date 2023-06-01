package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByUsername(String username);

    Optional<Player> findByUuid(String uuid);

}
