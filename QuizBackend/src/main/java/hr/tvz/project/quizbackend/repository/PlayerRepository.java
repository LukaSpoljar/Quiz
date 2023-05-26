package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByUsername(String username);
}
