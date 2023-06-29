package hr.tvz.project.quizbackend.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolveQuizAnswer {

    @NotNull
    Long questionId;

    @NotNull
    Long answerId;
}
