package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    public List<PlayerDB> getAllPlayers() {
        return playerRepository.findAll();
    }
    public Optional<PlayerDB> getPlayer(Long id) {
        return playerRepository.findById(id);
    }
    public Optional<PlayerDB> getPlayer(String username) {
        return playerRepository.findByUsername(username);
    }
    public Optional<PlayerDB> getPlayerByUuid(String uuid) {
        return playerRepository.findByUuid(uuid);
    }

    /**
     * Insert a player in database. Checks if player with given username exists in database before insertion.
     *
     * @param username Player's username
     * @param password Player's password (raw string; not hashed)
     * @return Optional object containing new player's information
     */
    public Optional<PlayerDB> createPlayer(String username, String password) {
        if(getPlayer(username).isEmpty()) {
            PlayerDB playerDB = new PlayerDB(username, password);
            playerRepository.save(playerDB);
            return Optional.of(playerDB);
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Checks is given user exists in database. If an entry exists it gets deleted from the database.
     *
     * @param username Player's username
     * @return boolean value of successful execution
     */
    public boolean deletePlayer(String username){
        if(getPlayer(username).isPresent()){
            playerRepository.delete(getPlayer(username).get());
            return true;
        }
        else
            return false;
    }

    /**
     *Checks presence of entity with given username in the database. Changes the password of the given user.
     *
     * @param username Player's username
     * @param password Player's password (raw string; not hashed)
     * @return An optional object. If present, the change was successful.
     */
    public Optional<PlayerDB> updatePassword(String username, String password){
        Optional<PlayerDB> playerDB = getPlayer(username);
        if(playerDB.isPresent()){
            playerDB.get().setHashedPassword(password);
            playerRepository.save(playerDB.get());
            return playerDB;
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Checks if parameters follow given format structure:
     *      -username must be between 3 and 10 characters long using alphanumerical symbols
     *      -password must be between 8 and 20 characters long not containing whitespace
     *      -botChecker parameter must be false, denoting a human user
     *
     * @param username Player's username
     * @param password Player's password (raw string; not hashed)
     * @param botChecker Hidden registration form field for bot checking
     * @return boolean value of successful execution
     */
    public boolean validateNewPlayer(String username, String password, boolean botChecker){
        boolean checker=true;
        if(botChecker || username.length()<3 ||
                username.length()>10 || !username.matches("[A-Za-z0-9]+") ||
                password.length()<8 || password.length()>20 || password.matches(".*\\s.*"))
        {
            checker=false;
        }
        return checker;
    }

}
