package hr.tvz.project.quizbackend.entity;

import hr.tvz.project.quizbackend.logic.HelperFunctions;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="player")
@SequenceGenerator(name="player_gen", sequenceName = "player_gen", initialValue=4)
public class PlayerDB {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "player_gen")
    private Long id;

    @Column(unique = true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String hashedPassword;

    @Column(unique = true, name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString().toUpperCase();

    public PlayerDB() {
    }

    public String getUuid() {
        return uuid;
    }

    public PlayerDB(String username, String password) {
        this.username = username;
        byte[] hashedPasswordBytes = HelperFunctions.stringToSha256(password);
        this.hashedPassword = HelperFunctions.bytesToHex(hashedPasswordBytes);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String nonHashedPassword) {
        byte[] hashedPasswordBytes = HelperFunctions.stringToSha256(nonHashedPassword);
        String hashedPassword = HelperFunctions.bytesToHex(hashedPasswordBytes);
        this.hashedPassword = hashedPassword;
    }

    /**
     * Check if input password matches the one in player.
     * Useful in login checking.
     *
     * @param password A raw password string (not hashed).
     * @return True if password hash matches the one in Player
     */
    public boolean checkPassword(String password) {
        byte[] hashedPasswordBytes = HelperFunctions.stringToSha256(password);
        String hashedPassword = HelperFunctions.bytesToHex(hashedPasswordBytes);

        boolean passwordsMatch = this.getHashedPassword().equals(hashedPassword);
        return passwordsMatch;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + hashedPassword + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

}
