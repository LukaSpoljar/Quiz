package hr.tvz.project.quizbackend.domain;

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

    // If present, it means that the client sent proper request
    Optional<QuizDTO> quiz;

    public static CreateQuizResponse invalid(String error) {
        CreateQuizResponse solveResponse = new CreateQuizResponse(Optional.of(error), Optional.empty());
        return solveResponse;
    }

    public static CreateQuizResponse valid(QuizDTO quizDTO) {
        CreateQuizResponse createResponse = new CreateQuizResponse(Optional.empty(), Optional.of(quizDTO));
        return createResponse;
    }

}
