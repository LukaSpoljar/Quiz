package hr.tvz.project.quizbackend.controllers;

import hr.tvz.project.quizbackend.entity.Player;
import hr.tvz.project.quizbackend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Optional<Player> player = playerService.getPlayer(id);
        if (player.isPresent())
        {
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping ("/register/{username}/{password}")
    public ResponseEntity<?> createPlayer(@PathVariable String username,@PathVariable String password)
    {
        if(playerService.createPlayer(username,password)){
        return new ResponseEntity<>(201, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(404, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deletePlayer(@PathVariable String username)
    {
        if(playerService.deletePlayer(username)){
            return new ResponseEntity<>(200, HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(404, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PutMapping("/update/{username}/{password}")
    public ResponseEntity<?> updatePlayer(@PathVariable String username, @PathVariable String password){
        if(playerService.updatePlayer(username,password)){
            return new ResponseEntity<>(200, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(404, HttpStatus.NOT_FOUND);
        }
    }
}
