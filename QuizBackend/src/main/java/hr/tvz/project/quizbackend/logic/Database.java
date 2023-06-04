package hr.tvz.project.quizbackend.logic;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.repository.PlayerRepository;

public class Database {

    public static void InitializePlayers(PlayerRepository playerRepository)
    {
        playerRepository.save(new PlayerDB("štef", "štef_lozinka"));
        playerRepository.save(new PlayerDB("luka", "luka_lozinka"));
        playerRepository.save(new PlayerDB("ivan", "ivan_lozinka"));
    }

}
