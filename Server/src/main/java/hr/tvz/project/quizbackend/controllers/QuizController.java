package hr.tvz.project.quizbackend.controllers;

import hr.tvz.project.quizbackend.domain.*;
import hr.tvz.project.quizbackend.entity.CategoryDB;
import hr.tvz.project.quizbackend.entity.QuizDB;
import hr.tvz.project.quizbackend.service.QuizService;
import hr.tvz.project.quizbackend.service.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("http://localhost:4200")
public class QuizController {

    private final QuizService quizService;
    private final ResultService quizResultService;

    @Autowired
    public QuizController(
            QuizService quizService,
            ResultService quizResultService
    ) {
        this.quizService = quizService;
        this.quizResultService = quizResultService;
    }

    @GetMapping
    //public ResponseEntity<List<QuizDTO>> getAllQuizzes()
    public ResponseEntity<List<QuizBasicDTO>> getAllQuizzes()
    {
        // Test with cURL and python:
        //   curl -X GET http://localhost:8080/quiz | python3 -m json.tool
        List<QuizDB> quizList = quizService.getAllQuizzes();
        List<QuizBasicDTO> quizDTOList = quizList.stream().map(quiz -> new QuizBasicDTO(quiz)).toList();
        return new ResponseEntity<>(quizDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id)
    {
        // Test with cURL and python:
        //   curl -X GET http://localhost:8080/quiz/1 | python3 -m json.tool
        Optional<QuizDB> quiz = quizService.getQuiz(id);
        if (quiz.isPresent()) {
            QuizDTO quizDTO = new QuizDTO(quiz.get());
            return new ResponseEntity<>(quizDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("QUIZ_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDB>> getCategories()
    {
        List<CategoryDB> categories = quizService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<QuizResultsCollectionDTO> getResults(@PathVariable Long id)
    {
        QuizResultsCollectionDTO resultsCollection = quizResultService.getByQuizId(id);
        return new ResponseEntity<>(resultsCollection, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(
         @Valid @RequestBody CreateQuizForm createQuizForm
    ) {
        CreateQuizResponse createResponse = quizService.createQuiz(createQuizForm);
        if (createResponse.getError().isPresent()) {
            return new ResponseEntity<>(createResponse.getError(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createResponse.getQuiz(), HttpStatus.OK);
    }

    @PostMapping("/solve")
    public ResponseEntity<?> solveQuiz(
        @Valid @RequestBody SolveQuizForm solveQuizForm
    ){
        SolveQuizResponse solveResponse = quizResultService.solveQuiz(solveQuizForm);
        if (solveResponse.getError().isPresent()) {
            return new ResponseEntity<>(solveResponse.getError(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(solveResponse.getResult(), HttpStatus.OK);
    }

}
