package hr.tvz.project.quizbackend.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @GetMapping
    public ResponseEntity<String> testResponse(){
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> postResponse(){
        return new ResponseEntity<>("GotIt", HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> putResponse(){
        return new ResponseEntity<>("Yes", HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResponse(@PathVariable Long id){
        return new ResponseEntity<>("Exterminate: "+id, HttpStatus.OK);
    }

}
