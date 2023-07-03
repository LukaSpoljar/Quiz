package hr.tvz.project.quizbackend.controllers;

import hr.tvz.project.quizbackend.domain.PlayerDTO;
import hr.tvz.project.quizbackend.domain.RegisterForm;
import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.entity.PlayerResponse;
import hr.tvz.project.quizbackend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/player")
@CrossOrigin("http://localhost:4200")
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

    @GetMapping("/all")
    public ResponseEntity<List<PlayerDTO>> getAll()
    {
        List<PlayerDTO> playerDTOList = playerService.getAllPlayers()
            .stream()
            .map(playerDB -> new PlayerDTO(playerDB))
            .toList();
        return new ResponseEntity<>(playerDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable Long id)
    {
        Optional<PlayerDB> playerDB = playerService.getPlayer(id);
        if (playerDB.isPresent())
        {
            // Convert to DTO object
            PlayerDTO playerDTO = new PlayerDTO(playerDB.get());
            return new ResponseEntity<>(playerDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping ("/register")
    public ResponseEntity<?> createPlayer(@RequestBody RegisterForm newPlayer)
    {
        PlayerResponse validateResponse = playerService.validateNewPlayer(newPlayer.getUsername(), newPlayer.getPassword(), newPlayer.getEmail());
        if(validateResponse==PlayerResponse.OK) {
            Optional<PlayerDB> createdPlayer = playerService.createPlayer(newPlayer.getUsername(), newPlayer.getPassword());
            if(createdPlayer.isPresent()){
                PlayerDTO playerDTO = new PlayerDTO(createdPlayer.get());
                return new ResponseEntity<>(playerDTO, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deletePlayer(@PathVariable String username)
    {
        if(playerService.deletePlayer(username)){
            return new ResponseEntity<>(200, HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(404, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePlayer(@RequestBody RegisterForm newPlayer){
        Optional<PlayerDB> updatedPlayer = playerService.updatePassword(newPlayer.getUsername(), newPlayer.getPassword());
        if(updatedPlayer.isPresent()){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPlayer(@RequestBody RegisterForm playerInfo){
        // First make player to hash password
        PlayerDB playerInit = new PlayerDB(playerInfo.getUsername(), playerInfo.getPassword());

        // Get player
        Optional<PlayerDB> optionalPlayer = playerService.login(playerInit.getUsername(), playerInit.getHashedPassword());
        if(optionalPlayer.isPresent()) {
            return new ResponseEntity<>(optionalPlayer.get().getUuid(), HttpStatus.OK);
        }

        // Else not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
