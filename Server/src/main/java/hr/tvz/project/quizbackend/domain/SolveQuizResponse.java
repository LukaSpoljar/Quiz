package hr.tvz.project.quizbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolveQuizResponse {

    // If present, it means that the client sent bad request
    Optional<String> error;

    // If present, it means that the client sent proper request
    Optional<QuizResultsRowDTO> result;

    public static SolveQuizResponse invalid(String error) {
        SolveQuizResponse solveResponse = new SolveQuizResponse(Optional.of(error), Optional.empty());
        return solveResponse;
    }

    public static SolveQuizResponse valid(QuizResultsRowDTO results) {
        SolveQuizResponse solveResponse = new SolveQuizResponse(Optional.empty(), Optional.of(results));
        return solveResponse;
    }

}
