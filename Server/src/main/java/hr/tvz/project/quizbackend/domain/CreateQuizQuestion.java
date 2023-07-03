package hr.tvz.project.quizbackend.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuizQuestion {

    @NotNull
    String text;

    @NotNull
    List<String> incorrectAnswers;

    @NotNull
    String correctAnswer;
}
