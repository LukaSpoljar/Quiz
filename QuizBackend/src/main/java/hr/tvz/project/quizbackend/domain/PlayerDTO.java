package hr.tvz.project.quizbackend.domain;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class used in communication with clients.
 * It hides vulnerable data such as password.
 * It may be initialized using PlayerDB class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private String username;
    private String uuid;
    // Password is hidden

    public PlayerDTO(PlayerDB playerDB) {
        this.username = playerDB.getUsername();
        this.uuid = playerDB.getUuid();
    }

}
