package hr.tvz.project.quizbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolveQuizAnswer {
    Long questionId;
    Long answerId;
}
