package hr.tvz.project.quizbackend.domain;

import hr.tvz.project.quizbackend.entity.QuizDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuizResponse {

    // If present, it means that the client sent bad request
    Optional<String> error;

    Optional<QuizDB> quiz;

    public static CreateQuizResponse invalid(String error) {
        CreateQuizResponse solveResponse = new CreateQuizResponse(Optional.of(error), Optional.empty());
        return solveResponse;
    }

    public static CreateQuizResponse valid(QuizDB quiz) {
        CreateQuizResponse createResponse = new CreateQuizResponse(Optional.empty(), Optional.of(quiz));
        return createResponse;
    }

}
