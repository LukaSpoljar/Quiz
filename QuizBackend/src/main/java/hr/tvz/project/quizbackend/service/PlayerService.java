package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.entity.Player;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Player> getPlayer(Long id)
    {
        return playerRepository.findById(id);
    }

    public Optional<Player> getPlayer(String username)
    {
        return playerRepository.findByUsername(username);
    }

}
