package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.entity.Player;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    //jdbcTemplate for searching player entity from DB
    /*private JdbcTemplate jdbc;
    private SimpleJdbcInsert playerInserter;

    public void JdbcPlayerRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
        this.playerInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Player")
                .usingGeneratedKeyColumns("id");
    }
        private Player mapRowToPlayer(ResultSet rs,int rowNum) throws SQLException{
        Player player = new Player();
        player.setId(rs.getLong("id"));
        player.setUsername(rs.getString("username"));
        player.setHashedPassword(rs.getString("hashedPassword"));
        return player;
    }*/
    //-------------------------------------------------

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
    public boolean createPlayer(String username, String password) {
            Player player = new Player(username, password);
            playerRepository.save(player);
            /*String playerUuid = player.getUuid();
            return playerUuid;*/
            return true;
    }

    public boolean deletePlayer(String username){
        if(getPlayer(username).isPresent()){
            playerRepository.delete(getPlayer(username).get());
            return true;
        }
        else
            return false;
    }

    public boolean updatePlayer(String username, String password){
        if(getPlayer(username).isPresent()){
            Player player = getPlayer(username).get();
            player.setHashedPassword(password);
            playerRepository.save(player);
            return true;
        }
        else
            return false;
    }
}
