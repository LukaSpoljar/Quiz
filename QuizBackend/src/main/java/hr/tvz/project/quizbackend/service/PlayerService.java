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

    public Optional<Player> getPlayer(Long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> getPlayer(String username) {
        return playerRepository.findByUsername(username);
    }

    public Optional<Player> getPlayerByUuid(String uuid) {
        return playerRepository.findByUuid(uuid);
    }

    /**
     * Insert a player in database. Does not perform any checking prior to add.
     *
     * @param username Player's username
     * @param password Player's password (raw string; not hashed)
     * @return New player's token (UUID)
     */
    public String createPlayer(String username, String password) {
        Player player = new Player(username, password);
        playerRepository.save(player);
        String playerUuid = player.getUuid();
        return playerUuid;
    }

}
