package hr.tvz.project.quizbackend.controllers;

import hr.tvz.project.quizbackend.domain.PlayerDTO;
import hr.tvz.project.quizbackend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService)
    {
        this.playerService = playerService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> allOk()
    {
        return new ResponseEntity<>("All is OK", HttpStatus.OK);
    }

    @GetMapping("/getPlayer/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable Long id)
    {
        Optional<PlayerDTO> player = playerService.getPlayer(id);
        if (player.isPresent())
        {
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
