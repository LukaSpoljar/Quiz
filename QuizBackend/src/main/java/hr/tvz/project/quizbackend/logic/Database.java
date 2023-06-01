package hr.tvz.project.quizbackend.logic;

import hr.tvz.project.quizbackend.entity.Player;
import hr.tvz.project.quizbackend.repository.PlayerRepository;

public class Database {

    public static void InitializePlayers(PlayerRepository playerRepository)
    {
        playerRepository.save(new Player("štef", "lozinka"));
        playerRepository.save(new Player("luka", "lozinka"));
        playerRepository.save(new Player("ivan", "lozinka"));
    }

}
