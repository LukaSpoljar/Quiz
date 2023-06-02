package hr.tvz.project.quizbackend.domain;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Player class used in client-server communication
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private String username;
    private String uuid;

    public PlayerDTO(PlayerDB playerDB) {
        this.username = playerDB.getUsername();
        this.uuid = playerDB.getUuid();
    }

}
