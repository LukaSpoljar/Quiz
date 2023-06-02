package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.domain.PlayerDTO;
import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    private Optional<PlayerDTO> convertPlayerDBToPlayerDTO(Optional<PlayerDB> playerDB) {
        if (playerDB.isEmpty()) {
            return Optional.empty();
        }
        else {
            PlayerDTO playerDTO = new PlayerDTO(playerDB.get());
            return Optional.of(playerDTO);
        }
    }

    public Optional<PlayerDTO> getPlayer(Long id) {
        Optional<PlayerDB> playerDB = playerRepository.findById(id);
        return convertPlayerDBToPlayerDTO(playerDB);
    }

    public Optional<PlayerDTO> getPlayer(String username) {
        Optional<PlayerDB> playerDB = playerRepository.findByUsername(username);
        return convertPlayerDBToPlayerDTO(playerDB);
    }

    public Optional<PlayerDTO> getPlayerByUuid(String uuid) {
        Optional<PlayerDB> playerDB = playerRepository.findByUuid(uuid);
        return convertPlayerDBToPlayerDTO(playerDB);
    }

    /**
     * Insert a player in database. Does not perform any checking prior to add.
     *
     * @param username Player's username
     * @param password Player's password (raw string; not hashed)
     * @return New player's token (UUID)
     */
    public String createPlayer(String username, String password) {
        PlayerDB playerDB = new PlayerDB(username, password);
        playerRepository.save(playerDB);
        String playerUuid = playerDB.getUuid();
        return playerUuid;
    }

}
